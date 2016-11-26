package utils.silhouette

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import models.{User, UserPassword}
import repo.{UserPasswordRepository, UserRepository}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  *
  */
class PasswordInfoDAO @Inject() (userRepository: UserRepository ,userPasswordRepository: UserPasswordRepository)
  extends DelegableAuthInfoDAO[PasswordInfo] {

  /**
    *
    * @param loginInfo
    * @return
    */
  private def lookupPassword(loginInfo: LoginInfo): Future[(Option[User], Option[UserPassword])] = {
    {
      for {
      Some(user) <- userRepository.findByEmail(loginInfo.providerKey)
      userPassword  <- userPasswordRepository.getById(user.id.get) if user.activated
      } yield { Some(user) -> userPassword }
    }.recover({case e: Throwable => None -> None})
  }


  override def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    lookupPassword(loginInfo) flatMap {
      case (Some(_), Some(x)) => Future.failed(throw new RuntimeException(s"user ${loginInfo.providerKey} already password entry"))
      case (Some(x), None) => {
           userPasswordRepository
             .insert(UserPassword(x.id.get, authInfo.password, authInfo.hasher, authInfo.salt))
             .map(z => authInfo)
      }
      case (None, _) => Future.failed(new Exception(s"user ${loginInfo.providerKey} doesn't exists"))
    }
  }

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = {
   lookupPassword(loginInfo) map {
     case (Some(x),Some(y)) => Some(y.toPasswordInfo)
     case _ => None
   }
  }

  override def remove(loginInfo: LoginInfo): Future[Unit] = {
    lookupPassword(loginInfo) map {
      case (Some(_),Some(x)) => userPasswordRepository.delete(x.userId)
      case _ => throw new RuntimeException(s"password doesnt exists for ${loginInfo.providerKey}")
    }
  }

  override def save(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] =
    lookupPassword(loginInfo).flatMap {
      case (Some(_), Some(_)) => update(loginInfo, authInfo)
      case (Some(_),None) => add(loginInfo, authInfo)
      case _ => Future.failed(new RuntimeException(s"user ${loginInfo.providerKey} doesn't exists"))
  }

  override def update(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    lookupPassword(loginInfo) flatMap {
      case (Some(_), Some(x)) => userPasswordRepository
        .update(UserPassword(x.userId, authInfo.password, authInfo.hasher, authInfo.salt)).map(y => authInfo)
      case _ => throw new RuntimeException(s"the user ${loginInfo.providerKey} must exists to update its password")
    }
  }


}
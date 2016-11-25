package utils.silhouette

import javax.inject.Inject

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import models.User
import repo.UserRepository
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by chlr on 11/24/16.
  */
class UserService @Inject() (userRepository: UserRepository) extends IdentityService[User] {

  def retrieve(loginInfo: LoginInfo): Future[Option[User]] = userRepository.findByEmail(loginInfo.providerKey)

  def save(user: User): Future[User] = userRepository.insert(user).map(x => user)

}

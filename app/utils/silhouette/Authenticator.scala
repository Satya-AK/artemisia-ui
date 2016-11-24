package utils.silhouette

import com.mohiva.play.silhouette.api.Authorization
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import models.UserRole.BaseUserRole
import models.{User, UserRole}
import play.api.mvc.Request

import scala.concurrent.Future

/**
  * Created by chlr on 11/24/16.
  */


case class Authenticator(minimumRole: BaseUserRole) extends Authorization[User, CookieAuthenticator]  {

  override def isAuthorized[B](identity: User, authenticator: CookieAuthenticator)(implicit request: Request[B]) = {
    Future.successful(UserRole.getUserRole(identity.roleId).isSubordinate(minimumRole))
  }

}

package utils.silhouette

import com.mohiva.play.silhouette.api.actions.{SecuredRequest, UserAwareRequest}
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import models.User
import play.api.i18n.I18nSupport
import play.api.mvc.Controller

/**
  * Created by chlr on 11/23/16.
  */

trait AuthController extends Controller with I18nSupport {

  def silhouette: Silhouette[AuthEnv]
  def env: Environment[AuthEnv] = silhouette.env

  def SecuredAction = silhouette.SecuredAction
  def UnsecuredAction = silhouette.UnsecuredAction
  def UserAwareAction = silhouette.UserAwareAction

  implicit def securedRequest2User[A](implicit request: SecuredRequest[AuthEnv, A]): User = request.identity
  implicit def userAwareRequest2UserOpt[A](implicit request: UserAwareRequest[AuthEnv, A]): Option[User] = request.identity

}

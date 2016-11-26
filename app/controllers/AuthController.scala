package controllers

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.util.{Credentials, PasswordHasherRegistry}
import com.mohiva.play.silhouette.api.{LoginEvent, LoginInfo, Silhouette}
import com.mohiva.play.silhouette.impl.exceptions.IdentityNotFoundException
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import models.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.mvc.Request
import utils.silhouette.{AuthEnv, AuthHelperController, UserService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by chlr on 11/25/16.
  */
class AuthController @Inject() (
                                 val silhouette: Silhouette[AuthEnv],
                                 userService: UserService,
                                 authInfoRepository: AuthInfoRepository,
                                 passwordHasherRegistry: PasswordHasherRegistry,
                                 credentialsProvider: CredentialsProvider
                               ) extends AuthHelperController {

  val signUpForm = Form(
    mapping(
      "id" -> ignored(None: Option[Int]),
      "email" -> email.verifying(maxLength(250)),
      "nickname" -> nonEmptyText,
      "firstname" -> ignored("harley"),
      "lastname" -> ignored("quinn"),
      "password" -> nonEmptyText.verifying(minLength(6)),
      "activated" -> ignored(true),
      "roleId" -> ignored(1)
    )(User.apply)(User.unapply)
  )

  val signInForm = Form(tuple(
    "email" -> email,
    "password" -> nonEmptyText
  ))


  def signUp = UnsecuredAction.async {
    implicit request =>
    signUpForm.bindFromRequest().fold(
      formWithErrors => Future.successful(BadRequest(formWithErrors.errors.head.key)),
      user => {
        val loginInfo: LoginInfo = LoginInfo("credentials",user.email)
        userService.retrieve(loginInfo).flatMap {
          case Some(_) => Future.successful(BadRequest("email already exists"))
          case None => {
            for {
              savedUser <- userService.save(user)
              _ <- authInfoRepository.add(loginInfo, passwordHasherRegistry.current.hash(user.password))
            } yield { Ok("User has been signed up") }
          }
        }
      }
    )
  }


  def authenticate =  UnsecuredAction.async {
    implicit request =>
            signInForm.bindFromRequest.fold(
              formWithErrors => Future.successful(BadRequest("incorrect form bindind")),
              formData =>  login(formData)
            )
     }

  private def login(formData: (String, String))(implicit request: Request[_]) = {
    val (identifier, password) = formData
    credentialsProvider.authenticate(Credentials(identifier, password)).flatMap { loginInfo =>
      userService.retrieve(loginInfo).flatMap {
        case Some(user) => for {
          authenticator <- env.authenticatorService.create(loginInfo)
          cookie <- env.authenticatorService.init(authenticator)
          result <- env.authenticatorService.embed(cookie, Ok("Successful Authentication"))
        } yield {
          env.eventBus.publish(LoginEvent(user, request))
          result
        }
        case None => Future.failed(new IdentityNotFoundException("Couldn't find user"))
      }
    }.recover {
      case e: ProviderException => {
        println(e.getClass.getCanonicalName)
        BadRequest(e.getMessage)
      }
    }
  }

}


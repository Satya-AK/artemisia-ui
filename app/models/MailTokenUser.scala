package models

import java.util.UUID

import org.joda.time.DateTime
import utils.silhouette.MailToken

/**
  * Created by chlr on 11/23/16.
  */

case class MailTokenUser(id: String, email: String, expirationTime: DateTime, isSignUp: Boolean) extends MailToken

object MailTokenUser {

  def apply(email: String, isSignUp: Boolean): MailTokenUser =
    MailTokenUser(UUID.randomUUID().toString, email, new DateTime().plusHours(24), isSignUp)

}

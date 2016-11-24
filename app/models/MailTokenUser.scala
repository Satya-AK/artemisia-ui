package models

import utils.silhouette.MailToken

/**
  * Created by chlr on 11/23/16.
  */


/**
  *
  * @param id
  * @param expirationTime
  * @param email
  * @param isSignUp
  */
case class MailTokenUser(
         id: String
         ,email: String
         ,expirationTime: java.sql.Timestamp
         ,isSignUp: Boolean) extends MailToken



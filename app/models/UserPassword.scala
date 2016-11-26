package models

import com.mohiva.play.silhouette.api.util.PasswordInfo

/**
  * Created by chlr on 11/26/16.
  */

/**
  *
  * @param userId user id
  * @param password hashed password
  * @param hasher hasher algorithm
  * @param salt salt
  */
case class UserPassword(userId: Int,
                       password: String,
                       hasher: String,
                       salt: Option[String]) {

  def toPasswordInfo = PasswordInfo(hasher, password, salt)

}




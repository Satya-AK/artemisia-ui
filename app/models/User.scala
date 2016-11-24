package models

import com.mohiva.play.silhouette.password.BCryptPasswordHasher
import utils.silhouette.IdentitySilhouette


/**
  * Created by chlr on 11/23/16.
  */


/**
  *
  * @param id
  * @param email
  * @param nickName
  * @param firstName
  * @param lastName
  * @param activated
  */
case class User(
                id: Option[Int],
                email: String,
                nickName: String,
                firstName: String,
                lastName: String,
                password: String,
                activated: Boolean,
                roleId: Int) extends
  IdentitySilhouette {

    def key = email

    def fullName: String = firstName + " " + lastName

  /**
    * creates a copy of the User model but with the password hashed
    * @return
    */
  def withEncryptPassword = this.copy(password =  new BCryptPasswordHasher().hash(password).password)

  }



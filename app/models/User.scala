package models

import com.mohiva.play.silhouette.api.Identity

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
                roleId: Int) extends Identity


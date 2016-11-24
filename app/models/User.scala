package models

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
                id: Option[Long],
                email: String,
                nickName: String,
                firstName: String,
                lastName: String,
                activated: Boolean,
                roleId: Int) extends
  IdentitySilhouette {
    def key = email
    def fullName: String = firstName + " " + lastName
  }

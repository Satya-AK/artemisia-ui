package utils.silhouette


import org.joda.time.DateTime

/**
  * Created by chlr on 11/23/16.
  */

trait MailToken {

  def id: String

  def email: String

  def expirationTime: DateTime

  def isExpired = expirationTime.isBeforeNow

}


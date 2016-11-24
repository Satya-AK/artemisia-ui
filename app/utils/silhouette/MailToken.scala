package utils.silhouette

import java.sql.Timestamp


/**
  * Created by chlr on 11/23/16.
  */

trait MailToken {

  def id: String

  def email: String

  def expirationTime: Timestamp

  def isExpired = new org.joda.time.DateTime(expirationTime.getTime).isBeforeNow

}


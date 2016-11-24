package utils.silhouette

import scala.concurrent.Future

/**
  * Created by chlr on 11/24/16.
  */
trait MailTokenService[T <: MailToken] {

  def create(token: T): Future[Option[T]]
  def retrieve(id: String): Future[Option[T]]
  def consume(id: String): Unit

}

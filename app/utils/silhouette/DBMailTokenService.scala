package utils.silhouette
import com.google.inject.Inject
import models.MailTokenUser
import repo.MailTokenRepository
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by chlr on 11/24/16.
  */
class DBMailTokenService @Inject() (mailTokenRepository: MailTokenRepository) extends MailTokenService[MailTokenUser] {

  override def create(token: MailTokenUser): Future[Option[MailTokenUser]] = {
    mailTokenRepository.insert(token).map(x => Some(token))
  }

  override def consume(id: String): Unit = {
    mailTokenRepository.delete(id)
  }

  override def retrieve(id: String): Future[Option[MailTokenUser]] = {
    mailTokenRepository.getById(id)
  }

}

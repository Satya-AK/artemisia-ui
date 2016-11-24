package repo

import javax.inject.Inject
import models.MailTokenUser
import play.api.Logger
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import scala.concurrent.Future

/**
  * Created by chlr on 11/23/16.
  */
class MailTokenRepository  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends
  MailTokenRepository.MailTokenRepoHelper with HasDatabaseConfigProvider[JdbcProfile] {

  private val logger = Logger(this.getClass)

  import driver.api._

  def insert(mailTokenUser: MailTokenUser): Future[String] = db.run {
    logger.debug(s"inserting Job: ${mailTokenUser.toString}")
    mailTokenUserTableQueryInc += mailTokenUser
  }

  def update(mailTokenUser: MailTokenUser): Future[Int] = db.run {
    logger.debug(s"updating user ${mailTokenUser.toString}")
    mailTokenUserTableQuery.filter(_.id === mailTokenUser.id).update(mailTokenUser)
  }

  def delete(id: String): Future[Int] = db.run {
    logger.debug(s"deleting mail token with $id")
    mailTokenUserTableQuery.filter(_.id === id).delete
  }

  def getAll: Future[List[MailTokenUser]] = db.run {
    logger.debug(s"retrieving all users")
    mailTokenUserTableQuery.to[List].result
  }

  def getById(jobId: String): Future[Option[MailTokenUser]] = db.run {
    mailTokenUserTableQuery.filter(_.id === jobId).result.headOption
  }

  def ddl = mailTokenUserTableQuery.schema

}

object MailTokenRepository {


  trait MailTokenRepoHelper {
    self: HasDatabaseConfigProvider[JdbcProfile] =>

    import driver.api._

    lazy protected val mailTokenUserTableQuery: TableQuery[MailTokenUserTable] = TableQuery[MailTokenUserTable]
    lazy protected val mailTokenUserTableQueryInc = mailTokenUserTableQuery returning mailTokenUserTableQuery.map(_.id)

    protected class MailTokenUserTable(tag: Tag) extends Table[MailTokenUser](tag, "mail_token_user") {

      val id = column[String]("id", O.PrimaryKey)
      val email = column[String]("email", O.SqlType("VARCHAR(100)"))
      val expirationTime = column[java.sql.Timestamp]("expiration_time")
      val isSignUp = column[Boolean]("is_signup")

      def * = (id, email, expirationTime, isSignUp) <> (MailTokenUser.tupled, MailTokenUser.unapply)
    }

  }


}

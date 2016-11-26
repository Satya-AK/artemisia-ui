package repo

import javax.inject.Inject
import models.UserPassword
import play.api.Logger
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repo.UserPasswordRepository.UserPasswordRepoHelper
import slick.driver.JdbcProfile
import scala.concurrent.Future

/**
  * Created by chlr on 11/26/16.
  */
class UserPasswordRepository  @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends
  UserPasswordRepoHelper with HasDatabaseConfigProvider[JdbcProfile] {


  private val logger = Logger(this.getClass)

  import driver.api._

  def insert(userPassword: UserPassword): Future[Int] = db.run {
    logger.debug(s"updating user ${userPassword.userId}")
    userPasswordTableQueryInc += userPassword
  }

  def update(userPassword: UserPassword): Future[Int] = db.run {
    logger.debug(s"updating user ${userPassword.userId}")
    userPasswordTableQuery.filter(_.userId === userPassword.userId).update(userPassword)
  }

  def delete(userId: Int): Future[Int] = db.run {
    logger.debug(s"deleting user_id $userId")
    userPasswordTableQuery.filter(_.userId === userId).delete
  }

  def getById(userID: Int): Future[Option[UserPassword]] = db.run {
    userPasswordTableQuery.filter(_.userId === userID).result.headOption
  }

  def ddl = userPasswordTableQuery.schema

}


object UserPasswordRepository {

  trait UserPasswordRepoHelper {

    self: HasDatabaseConfigProvider[JdbcProfile]  =>

    import driver.api._

    lazy protected val userPasswordTableQuery: TableQuery[UserPasswordTable] = TableQuery[UserPasswordTable]
    lazy protected val userPasswordTableQueryInc = userPasswordTableQuery returning userPasswordTableQuery.map(_.userId)

    protected class UserPasswordTable(tag: Tag) extends Table[UserPassword](tag, "user_password") {

      val userId = column[Int]("id", O.AutoInc, O.PrimaryKey)
      val password = column[String]("password", O.SqlType("VARCHAR(100)"))
      val hasher = column[String]("hasher", O.SqlType("VARCHAR(100)"))
      val salt = column[String]("salt", O.SqlType("VARCHAR(100)"))

      def * = (userId, password, hasher, salt.?) <> (UserPassword.tupled, UserPassword.unapply)
    }
  }

}
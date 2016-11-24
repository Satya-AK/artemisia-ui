package repo

import javax.inject.Inject
import models.UserRole
import play.api.Logger
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import scala.concurrent.Future

/**
  * Created by chlr on 11/23/16.
  */
class UserRoleRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends
  UserRoleRepository.UserRoleRepoHelper with HasDatabaseConfigProvider[JdbcProfile] {

  private val logger = Logger(this.getClass)

  import driver.api._

  def getAll: Future[List[UserRole]] = db.run {
    logger.debug(s"retrieving all users")
    userRoleTableQuery.to[List].result
  }

  def getById(jobId: Int): Future[Option[UserRole]] = db.run {
    userRoleTableQuery.filter(_.id === jobId).result.headOption
  }

  def ddl = userRoleTableQuery.schema

}

object UserRoleRepository {

  trait UserRoleRepoHelper {
    self: HasDatabaseConfigProvider[JdbcProfile] =>

    import driver.api._

    lazy protected val userRoleTableQuery: TableQuery[UserRoleTable] = TableQuery[UserRoleTable]

    protected class UserRoleTable(tag: Tag) extends Table[UserRole](tag, "user_role") {

      val id = column[Int]("id", O.AutoInc, O.PrimaryKey)
      val roleName = column[String]("name", O.SqlType("VARCHAR(40)"))

      def * = (id, roleName) <> ((UserRole.apply _).tupled, UserRole.unapply)

    }

  }

}

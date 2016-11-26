package controllers

import java.io.StringWriter

import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{Action, Controller}
import repo.UserPasswordRepository.UserPasswordRepoHelper
import repo.UserRepository.UserRepoHelper
import repo.UserRoleRepository.UserRoleRepoHelper
import slick.driver.JdbcProfile

/**
  * Created by chlr on 11/25/16.
  */
class ModelController @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends Controller
  with HasDatabaseConfigProvider[JdbcProfile] with UserRepoHelper with UserRoleRepoHelper with UserPasswordRepoHelper {

  import driver.api._

  def generate = Action {
    val allSchemas = userTableQuery.schema :: userRoleTableQuery.schema :: userPasswordTableQuery.schema  :: Nil
    val writer = new StringWriter()

    writer.write("# --- !Ups\n\n")

    for (schema <- allSchemas) {
      schema.createStatements.foreach { s => writer.write(s + ";\n") }
    }

    writer.write("\n\n# --- !Downs\n\n")

    for (schema <- allSchemas) {

      schema.dropStatements.foreach { s => writer.write(s + ";\n") }
    }
    writer.close()
    Ok(writer.toString)
  }



}

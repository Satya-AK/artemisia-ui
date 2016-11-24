package models

/**
  * Created by chlr on 11/23/16.
  */

/**
  *
  * @param id profile id
  * @param roleName name of the profile
  */
case class UserRole(id: Int, roleName: String)

object UserRole {

  def getUserRole(roleId: Int) = {
    roleId match {
      case 1 => AdminRole
      case 2 => DevRole
    }
  }

  sealed trait BaseUserRole {
    def isSubordinate(role: BaseUserRole): Boolean
  }

  object DevRole extends BaseUserRole {
    override def isSubordinate(role: BaseUserRole) = {
      role match {
        case AdminRole => false
        case _ => true
      }
    }
  }

  object AdminRole extends BaseUserRole {
    override def isSubordinate(role: BaseUserRole) = {
      role match {
        case _ => true
      }
    }
  }

}

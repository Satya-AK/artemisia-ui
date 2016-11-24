package utils.silhouette

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import Implicits._

/**
  * Created by chlr on 11/23/16.
  */

trait IdentitySilhouette extends Identity {
  def key: String
  def loginInfo: LoginInfo = key
}

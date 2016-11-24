package utils.silhouette

import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import models.User

/**
  * Created by chlr on 11/23/16.
  */



trait AuthEnv extends Env {
  type I = User
  type A = CookieAuthenticator
}

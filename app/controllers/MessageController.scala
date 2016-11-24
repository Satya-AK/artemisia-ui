package controllers

import play.api.mvc.{Action, Controller}


object MessageController extends Controller {


  def dummy = Action {
    Ok("dummy")
  }

}
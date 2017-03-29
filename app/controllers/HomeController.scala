package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.PlacesService


@Singleton
class HomeController @Inject()(val placesService: PlacesService) extends Controller {

  val form = Form(
    tuple(
      "firstname" -> text,
      "lastname" -> text
    )
  )

  def index = Action {
    Ok(views.html.index("Your new application is ready.", placesService.autocomplete("uk")))
  }

  def submit = Action { implicit request =>
    val (fname, lname) = form.bindFromRequest.get
    Ok("Hi %s %s".format(fname, lname))
  }

}

package controllers


import javax.inject._

import play.api.mvc._



/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, af: AssetsFinder) (implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {


  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */



  def showOrderForm() = Action {
    Ok(views.html.orderManagment.orderForm())
  }


  def showListOfOrders = Action {
    Ok(views.html.orderManagment.listOrders())
  }

}

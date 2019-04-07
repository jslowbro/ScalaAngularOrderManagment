package controllers

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import models.{Item, Order, StockItem}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.DBO

@Singleton
class APIController @Inject()(cc: ControllerComponents, af: AssetsFinder, dbo: DBO)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc)  {

  //handling Jsons

  implicit val jsonwriterItem = Json.writes[Item]

  implicit val jsonreaderItem = Json.reads[Item]

  implicit val jsonwriterOrder = Json.writes[Order]

  implicit val jsonwriterStockItem = Json.writes[StockItem]

  implicit val jsonreaderStockItem = Json.reads[StockItem]





  def getListOfOrders() = Action {
    val list = dbo.getOrderList
    val json = Json.toJson(list)

    Ok(json)
  }

  def postListOfItems() = Action { implicit request =>
      //parsing Json to a list
      val json  = request.body.asJson.get
      val list: List[Item] = json.as[List[Item]]
      dbo.postItems(list)
      Ok
  }


  def getStock() = Action {
    val list = dbo.getStock
    val json = Json.toJson(list)

    Ok(json)

  }

  def upateStockInfo() = Action { implicit request =>
    //parsing Json to a list
    val json = request.body.asJson.get
    val list: List[StockItem] = json.as[List[StockItem]]
    dbo.updateStock(list)
    Ok
  }

}

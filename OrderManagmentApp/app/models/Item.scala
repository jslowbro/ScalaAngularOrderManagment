package models

import play.api.libs.json.{JsResult, JsSuccess, JsValue}

case class Item(name: String, age: Int, size: String , color: String) {

  //Json working it's magic
  def reads(json: JsValue) : JsResult[Item] = {
    val name: String = (json \ "name").as[String]
    val age: Int = (json \ "age").as[Int]
    val size: String = (json \ "size").as[String]
    val color: String = (json \ "color").as[String]
    JsSuccess(Item(name,age,size,color))
  }

}

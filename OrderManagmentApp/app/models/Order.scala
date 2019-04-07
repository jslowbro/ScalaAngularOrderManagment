package models

import java.sql.Timestamp

case class Order(id: Int, timestamp: String, list: List[Item]) {

}

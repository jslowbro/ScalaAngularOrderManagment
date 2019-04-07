package services

import java.sql._

import javax.inject.Inject
import models.{Item, Order, StockItem}
import play.api.db._
import play.api.mvc.{BaseController, ControllerComponents}

import scala.collection.mutable.ListBuffer

class DBO @Inject()(db: Database, val controllerComponents: ControllerComponents) extends BaseController {


  val url = "jdbc:mysql://localhost/shoesdb"
  val driver = "com.mysql.jdbc.Driver"
  val username = "root"
  val password = ""


  def postItems(list: List[Item]) = {
    val conn = db.getConnection()

    try {
      //new Order
      val insertOrderPrepQuery = "INSERT INTO orders(timestamp) VALUES(?)"
      val statement1 = conn.prepareStatement(insertOrderPrepQuery)
      statement1.setTimestamp(1, new Timestamp(System.currentTimeMillis()))
      statement1.execute()
      //get new Order Id
      val getThatOrderId = "SELECT COUNT(*) FROM orders"
      val statement2 = conn.createStatement()
      val resultSet2 = statement2.executeQuery(getThatOrderId)
      resultSet2.next()
      val count = resultSet2.getInt(1)
      //adding List of Items assigned to an Order
      for(item <- list){
        val insertItemPrepQuery = "INSERT INTO items(name,age,size,color,order_id) VALUES(?,?,?,?,?)"
        val statement3 = conn.prepareStatement(insertItemPrepQuery)
        statement3.setString(1, item.name )
        statement3.setInt(2, item.age)
        statement3.setString(3, item.size )
        statement3.setString(4, item.color )
        statement3.setInt(5, count)
        statement3.execute()
      }

    } catch {
      case e: Exception => e printStackTrace()
    } finally {
      conn.close()
    }
  }

  def getOrderList: List[Order] = {
    //init
    val conn = db.getConnection()
    val tempOrderList = ListBuffer[Order]()
    val finOrderList = ListBuffer[Order]()

    try {
      //getting all Orders
      val getAllOrdersQuery = "SELECT * FROM orders"
      val getOrdersStatement = conn.createStatement()
      val ordersResultSet = getOrdersStatement.executeQuery(getAllOrdersQuery)
      while(ordersResultSet.next()){
        val order = Order(ordersResultSet.getInt("id"), ordersResultSet.getTimestamp("timestamp").toString, List())
        tempOrderList += order
      }

      //Assigning itemLists to each order
      for(order <- tempOrderList){
        //getting the itemlist assigned to an order
        val prepGetItemsByOrderId = "SELECT * FROM `orders` JOIN `items` ON order_id = orders.id WHERE orders.id = ?"
        val getItemsByOrderIdStmnt = conn.prepareStatement(prepGetItemsByOrderId)
        getItemsByOrderIdStmnt.setInt(1,order.id)
        val itemsResultSet = getItemsByOrderIdStmnt.executeQuery()
        //populating the list
        val itemList: ListBuffer[Item] = ListBuffer[Item]()
        while(itemsResultSet.next()){
          val item: Item = Item(itemsResultSet.getString("name"), itemsResultSet.getInt("age"), itemsResultSet.getString("size"), itemsResultSet.getString("color"))
          itemList += item
        }
        //adding the Order with the correct item List
        finOrderList += Order(order.id, order.timestamp, itemList.toList)
      }

    } catch {
      case e: SQLException => e.printStackTrace()

    } finally {
      conn.close()

    }
    finOrderList.toList
  }


  def getStock: List[StockItem] = {
    val conn = db.getConnection()
    val stockList: ListBuffer[StockItem] = ListBuffer[StockItem]()

    try {
      val query = "SELECT  * FROM `stock`"
      val statement = conn.createStatement()
      val resultSet = statement.executeQuery(query)
      while(resultSet.next()){
        val item = StockItem(resultSet.getInt("id"),resultSet.getString("size"), resultSet.getString("color"), resultSet.getInt("quantity"))
        stockList += item
      }
    } catch {
      case e: SQLException => e.printStackTrace()
    } finally {
      conn.close()
    }

    stockList.toList
  }


  def updateStock(list: List[StockItem]) = {
    val conn = db.getConnection()
    try {
      //updating every stockitem's quantity
      for (stockItem <- list) {
        //prepping the statement
        val prepQuery = "UPDATE stock SET quantity = ? WHERE size=? AND color=?"
        val prepStmnt = conn.prepareStatement(prepQuery)
        prepStmnt.setInt(1, stockItem.quantity)
        prepStmnt.setString(2, stockItem.size)
        prepStmnt.setString(3, stockItem.color)
        //executing the statement
        prepStmnt.execute()
      }

    } catch {
      case e: SQLException => e.printStackTrace()
    } finally {
      conn.close()
    }

  }


}

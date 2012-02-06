package no.capgemini.tradesim

import scala.collection.mutable.ListBuffer

class Consumer(market : Market, cash: Double, name: String) extends Agent(cash, name) {
	def createOrders(currentTime: Int) : ListBuffer[Order] = {
	  val validTime = 0;
	  var orderList = ListBuffer[Order]()
      Goods.values.foreach(goods => {
        orderList+= new BuyingOrder(market, this, goods, cash/Goods.values.size, market.population, validTime, currentTime)

      })
	  
	  return orderList
	}
}
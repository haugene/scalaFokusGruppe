package no.capgemini.tradesim

import scala.Math
import scala.util.Random
import scala.collection.mutable.ListBuffer

class Producer(market : Market, cash: Double, name: String, produces : Goods.Goods, basePrice: Double) extends Agent(cash, name) {
	def createOrders(currentTime: Int) : ListBuffer[Order] = {
	  val goods = this.produces
	  val price = this.basePrice
	  val validTime = 1
	  return ListBuffer[Order](new SellingOrder(market, this, goods, price, market.population+10, validTime, currentTime))
	}
	
}
package no.capgemini.tradesim

import scala.Math
import scala.util.Random

class Producer(market : Market, cash: Double, produces : Goods.Goods, basePrice: Double) extends Actor(market, cash) {
	def createOrder() : Order = {
	  val goods = this.produces
	  val price = this.basePrice
	  val validTime = 1;
	  return new SellingOrder(this, goods, price, 1, validTime)
	}
	
}
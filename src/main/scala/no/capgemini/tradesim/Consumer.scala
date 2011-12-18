package no.capgemini.tradesim

import scala.Math
import scala.util.Random

class Consumer(market : Market, consumes : Goods.Goods, basePrice: Integer) extends Actor(market) {
	def createOrder() : Order = {
	  val goods = this.consumes
	  val price = this.basePrice;
	  val validTime = 1;
	  return new BuyingOrder(this, goods, price, 1, validTime)
	}
}
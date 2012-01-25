package no.capgemini.tradesim

import scala.Math
import scala.util.Random

class Consumer(market : Market, cash: Double, consumes : Goods.Goods, basePrice: Double) extends Actor(market, cash) {
	def createOrder() : Order = {
	  val goods = this.consumes
	  val price = this.basePrice;
	  val validTime = 1;
	  return new BuyingOrder(this, goods, price, 1, validTime)
	}
}
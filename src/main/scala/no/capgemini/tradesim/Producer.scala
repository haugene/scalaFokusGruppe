package no.capgemini.tradesim

import scala.Math
import scala.util.Random

class Producer(market : Market, produces : Goods.Goods) extends Actor(market) {
	def createOrder() : Order = {
	  val goods = Random.shuffle(Goods.values).first
	  val price = (Math.random*100).toInt
	  val validTime = 1;
	  return new Order(goods, price, validTime)
	}
	
}
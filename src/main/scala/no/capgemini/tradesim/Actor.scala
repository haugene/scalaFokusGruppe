package no.capgemini.tradesim
import scala.collection.mutable.Map;

abstract class Actor(var market : Market, var cash : Double) {
	var goods = Map[Goods.Goods, Int]()
	def createOrder(): Order
}
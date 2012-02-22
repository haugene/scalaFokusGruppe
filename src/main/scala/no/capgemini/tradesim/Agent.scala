package no.capgemini.tradesim
import scala.collection.mutable.Map;
import scala.collection.mutable.ListBuffer

abstract class Agent(var cash : Double, val name : String) {
	var goods = Map[Goods.Goods, Int]()
	def createOrders(currentTime: Int): ListBuffer[Order]
	override def toString() = name
}


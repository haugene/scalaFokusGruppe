package no.capgemini.tradesim
import scala.collection.mutable.ListBuffer
import scala.util.Random

class Trader(cash: Double, name: String) extends Agent(cash, name) {

  def createOrders(currentTime: Int): ListBuffer[Order] = {
    val shuffledMarkets = Random.shuffle(World.currentWorld.markets.toList)
    
    val market = shuffledMarkets(0)
    
    val foundOrder = market.sellingOrders(0)
    val buyingPrice = foundOrder.price
    
    val number = (cash/buyingPrice).toInt
    val goods = foundOrder.goods
    var listOrders = ListBuffer[Order](
      new BuyingOrder(market, this, goods, buyingPrice, number, 0, currentTime),
      new SellingOrder(market, this, goods, buyingPrice+5, number, 1, currentTime))
    
    return listOrders
  }

}
package no.capgemini.tradesim

class BuyingOrder(market: Market, agent: Agent, goods: Goods.Goods, price: Double, amount: Int, validTime: Int, placedTime: Int) extends Order(market, agent, goods, price, amount, validTime, placedTime) with Ordered[BuyingOrder]{
  def compare(that: BuyingOrder) : Int = {
    var priceDiff = this.price - that.price;
    if (priceDiff > 0.0 ) {
      return -1
    } else if (priceDiff < 0.0) {
      return 1
    } else {
    	return  this.placedTime - that.placedTime
    }
    return 0;
  }

}
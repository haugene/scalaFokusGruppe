package no.capgemini.tradesim

class SellingOrder(market: Market, agent: Agent, goods: Goods.Goods, price: Double, amount: Int, validTime: Int, placedTime: Int) extends Order(market, agent, goods, price, amount, validTime, placedTime) with Ordered[SellingOrder] {

  def compare(that: SellingOrder) : Int = {
    var priceDiff = this.price - that.price;
    if (priceDiff > 0.0 ) {
      return 1
    } else if (priceDiff < 0.0) {
      return -1
    } else {
    	return  this.placedTime - that.placedTime
    }
  }
  
  override def hashCode(): Int = {
    return agent.name.hashCode() + goods.hashCode()
  }
        
}
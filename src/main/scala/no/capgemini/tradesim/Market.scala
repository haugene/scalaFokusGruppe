package no.capgemini.tradesim

import scala.collection.mutable.Set
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer
import no.capgemini.tradesim.SellingOrder

class Market(name: String, population: Integer) {
  var sellingOrders = ListBuffer[SellingOrder]()
  var buyingOrders = ListBuffer[BuyingOrder]()
  var actors = ListBuffer[Actor]()
  var demand = Map[Goods.Goods, Int]()

  def addOrder(o: Order) {
    if (o.isInstanceOf[SellingOrder]) {
      System.out.println("Adding selling order "+o.goods+" "+o.amount+" "+o.price);
      
      var s = matchBuyingOrders(o.asInstanceOf[SellingOrder]);
      if (s.amount >0 ) {
    	  sellingOrders += o.asInstanceOf[SellingOrder];
        
      }
    } else if (o.isInstanceOf[BuyingOrder]) {
      System.out.println("Adding buying order "+o.goods+" "+o.amount+" "+o.price);
      buyingOrders += o.asInstanceOf[BuyingOrder];
    }
  }

//  def addSellingOrder(s: SellingOrder) {
//    sellingOrders += s;
//  }
//
//  def addBuyingOrder(b: BuyingOrder) {
//    buyingOrders += b;
//  }

  def matchBuyingOrders(s: SellingOrder) : SellingOrder = {
    //Do this with an iterator or something instead
    var buyingOrdersToRemove = ListBuffer[BuyingOrder]()
    var i=1
    System.out.println("Size of buying orders: "+buyingOrders.size);
    for (buyingOrder <- buyingOrders) {
      System.out.println("loop no: "+i)
      if (buyingOrder.goods == s.goods && s.amount > 0 && buyingOrder.price <= s.price) {
        if (s.amount >= buyingOrder.amount) {
          System.out.println("Whole buying order matches")
          var transactionAmount = buyingOrder.amount
          var transactionPrice = buyingOrder.price
          var transactionTotal = transactionAmount * transactionPrice

          var buyer = buyingOrder.actor
          var seller = s.actor

          buyer.cash -= transactionTotal
          seller.cash += transactionTotal

          seller.goods.put(s.goods, seller.goods.get(s.goods).get - s.amount)
          buyer.goods.put(s.goods, buyer.goods.get(s.goods).get + s.amount)

          s.amount -= transactionAmount
          buyingOrdersToRemove += buyingOrder

        } else {
          System.out.println("Whole selling order matches")
          var transactionAmount = s.amount
          var transactionPrice = buyingOrder.price
          var transactionTotal = transactionAmount * transactionPrice

          var buyer = buyingOrder.actor
          var seller = s.actor

          buyer.cash -= transactionTotal
          seller.cash += transactionTotal

          seller.goods.put(s.goods, seller.goods.get(s.goods).get - s.amount)
          buyer.goods.put(s.goods, buyer.goods.get(s.goods).get + s.amount)

          s.amount -= transactionAmount
        }
      }
    }
    for (buyingOrder <- buyingOrdersToRemove) {
      buyingOrders -= buyingOrder
    }
    
    return s
  }

}
	
	

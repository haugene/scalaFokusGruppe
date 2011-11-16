package no.capgemini.tradesim

import scala.collection.mutable.Set;

class Market {
  /* 
   * Orders must be unique with respect to Actor and Goods.
   * Maybe implement Map with (Actor, Goods) tuple?
   */
	var sellingOrders = Set[SellingOrder]()
	var buyingOrders = Set[BuyingOrder]()
	
	def addOrder(o: Order) {
	  if (o.isInstanceOf[SellingOrder]) {
	    sellingOrders += o.asInstanceOf[SellingOrder];
	  } else if (o.isInstanceOf[BuyingOrder]) {
	    buyingOrders += o.asInstanceOf[BuyingOrder];
	  }
	}
	
	def addSellingOrder(s: SellingOrder) {
	  sellingOrders += s
	}
	
	def addBuyingOrder(b : BuyingOrder) {
	  buyingOrders += b
	}
	
	
}
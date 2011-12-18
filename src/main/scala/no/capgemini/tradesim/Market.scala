package no.capgemini.tradesim

import scala.collection.mutable.Set;
import scala.collection.mutable.Map;

class Market {
  /* 
   */
	var sellingOrders = Map[(Actor, Goods.Goods),SellingOrder]()
	var buyingOrders = Map[(Actor, Goods.Goods),BuyingOrder]()
	
	def addOrder(o: Order) {
	  if (o.isInstanceOf[SellingOrder]) {
	    sellingOrders.put((o.actor, o.goods), o.asInstanceOf[SellingOrder]);
	    //matchBuyingOrders(o.asInstanceOf[SellingOrder]);
	  } else if (o.isInstanceOf[BuyingOrder]) {
	    buyingOrders.put((o.actor, o.goods), o.asInstanceOf[BuyingOrder]);
	  }
	}
	
	def addSellingOrder(s: SellingOrder) {
	    sellingOrders.put((s.actor, s.goods), s);
	}
	
	def addBuyingOrder(b : BuyingOrder) {
	    buyingOrders.put((b.actor, b.goods), b);
	}
	
	
}
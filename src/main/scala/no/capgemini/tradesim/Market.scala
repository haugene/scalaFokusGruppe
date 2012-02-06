package no.capgemini.tradesim

import scala.collection.mutable.ListBuffer


class Market(var name: String, var population: Integer) {
  var sellingOrders = ListBuffer[SellingOrder]()
  var buyingOrders = ListBuffer[BuyingOrder]()
  var producers= ListBuffer[Producer]()
  var consumers= ListBuffer[Consumer]()

  def addOrder(o: Order) {
    if (o.isInstanceOf[SellingOrder]) {
      
      var s = matchBuyingOrders(o.asInstanceOf[SellingOrder]);
      if (s.amount >0 && s.validTime > 0) {
          addAndOverwriteSellingOrder(o.asInstanceOf[SellingOrder])
    	  //sellingOrders += o.asInstanceOf[SellingOrder];
        
      }
    } else if (o.isInstanceOf[BuyingOrder]) {
      var s = matchSellingOrders(o.asInstanceOf[BuyingOrder]);
      if (s.amount >0 && s.validTime > 0) {
    	  //buyingOrders += o.asInstanceOf[BuyingOrder];
        addAndOverwriteBuyingOrder(o.asInstanceOf[BuyingOrder])
        
      }
    }
  }
  
  def addAndOverwriteSellingOrder(order: SellingOrder) {
    sellingOrders.foreach(oldOrder => {
      if (oldOrder.agent == order.agent && oldOrder.goods == order.goods) {
    	  sellingOrders -= oldOrder
      }
    })
    sellingOrders += order
  }

  def addAndOverwriteBuyingOrder(order: BuyingOrder) {
    buyingOrders.foreach(oldOrder => {
      if (oldOrder.agent == order.agent && oldOrder.goods == order.goods) {
    	  buyingOrders -= oldOrder
      }
    })
    buyingOrders += order
  }

  /**
   * TODO: Needs a proper rewrite
   */
  def matchBuyingOrders(s: SellingOrder) : SellingOrder = {
    //Do this with an iterator or something instead
    var buyingOrdersToRemove = ListBuffer[BuyingOrder]()
    var i=1
    for (buyingOrder <- buyingOrders) {
      if (buyingOrder.goods == s.goods && s.amount > 0 && buyingOrder.price >= s.price) {
        if (s.amount >= buyingOrder.amount) {
          var transactionAmount = buyingOrder.amount
          var transactionPrice = buyingOrder.price
          var transactionTotal = transactionAmount * transactionPrice

          var buyer = buyingOrder.agent
          var seller = s.agent

          buyer.cash -= transactionTotal
          seller.cash += transactionTotal

          seller.goods.put(s.goods, seller.goods.get(s.goods).getOrElse(0) - transactionAmount)
          buyer.goods.put(s.goods, buyer.goods.get(s.goods).getOrElse(0) + transactionAmount)

          s.amount -= transactionAmount
          buyingOrder.amount -= transactionAmount
          buyingOrdersToRemove += buyingOrder

        } else {
          var transactionAmount = s.amount
          var transactionPrice = buyingOrder.price
          var transactionTotal = transactionAmount * transactionPrice

          var buyer = buyingOrder.agent
          var seller = s.agent

          buyer.cash -= transactionTotal
          seller.cash += transactionTotal

          seller.goods.put(s.goods, seller.goods.get(s.goods).getOrElse(0) - transactionAmount)
          buyer.goods.put(s.goods, buyer.goods.get(s.goods).getOrElse(0) + transactionAmount)

          buyingOrder.amount -= transactionAmount
          s.amount -= transactionAmount
        }
      }
    }
    for (buyingOrder <- buyingOrdersToRemove) {
      buyingOrders -= buyingOrder
    }
    
    return s
  }

  /**
   * TODO: Needs a proper rewrite
   */
  def matchSellingOrders(b: BuyingOrder) : BuyingOrder = {
    //Do this with an iterator or something instead
    var sellingOrdersToRemove = ListBuffer[SellingOrder]()
    var i=1
    for (sellingOrder <- sellingOrders) {
      if (sellingOrder.goods == b.goods && b.amount > 0 && sellingOrder.price <= b.price) {
        if (b.amount >= sellingOrder.amount) {
          var transactionAmount = sellingOrder.amount
          var transactionPrice = sellingOrder.price
          var transactionTotal = transactionAmount * transactionPrice

          var seller = sellingOrder.agent
          var buyer = b.agent

          buyer.cash -= transactionTotal
          seller.cash += transactionTotal

          seller.goods.put(b.goods, seller.goods.get(b.goods).getOrElse(0) - transactionAmount)
          buyer.goods.put(b.goods, buyer.goods.get(b.goods).getOrElse(0) + transactionAmount)

          b.amount -= transactionAmount
          sellingOrder.amount -= transactionAmount
          sellingOrdersToRemove += sellingOrder

        } else {
          var transactionAmount = b.amount
          var transactionPrice = sellingOrder.price
          var transactionTotal = transactionAmount * transactionPrice

          var seller = sellingOrder.agent
          var buyer = b.agent

          buyer.cash -= transactionTotal
          seller.cash += transactionTotal

          seller.goods.put(b.goods, seller.goods.get(b.goods).getOrElse(0) - transactionAmount)
          buyer.goods.put(b.goods, buyer.goods.get(b.goods).getOrElse(0) + transactionAmount)

          sellingOrder.amount -= transactionAmount
          b.amount -= transactionAmount
        }
      }
    }
    for (sellingOrder <- sellingOrdersToRemove) {
      sellingOrders -= sellingOrder
    }
    
    return b
  }
}
	
	

package no.capgemini.tradesim

import scala.collection.mutable.ListBuffer


class Market(var name: String, var population: Integer) {
  var sellingOrders = ListBuffer[SellingOrder]()
  var buyingOrders = ListBuffer[BuyingOrder]()
  var producers= ListBuffer[Producer]()
  var consumers= ListBuffer[Consumer]()
  var history = ListBuffer[Order]()

  def addOrder(o: Order) {
    
    history += o
	if (o.price < 0 || o.amount <= 0) {
	  return
	} 
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

  def matchBuyingOrders(sellingOrder: SellingOrder) : SellingOrder = {
    for (buyingOrder <- buyingOrders) {
      if (buyingOrder.goods == sellingOrder.goods && sellingOrder.amount > 0 && buyingOrder.price >= sellingOrder.price) {
        //Can only match up to whichever amount is largest
        matchSingleOrder(buyingOrder, sellingOrder, buyingOrder.price)
        if (buyingOrder.amount <= 0) 
          buyingOrders -= buyingOrder
      }
    }
    
    return sellingOrder
  }

  def matchSellingOrders(buyingOrder: BuyingOrder) : BuyingOrder = {
    for (sellingOrder <- sellingOrders) {
      if (sellingOrder.goods == buyingOrder.goods && buyingOrder.amount > 0 && sellingOrder.price <= buyingOrder.price) {
        matchSingleOrder(buyingOrder, sellingOrder, sellingOrder.price)
        if (sellingOrder.amount <= 0) 
          sellingOrders -= sellingOrder
      }
    }
    
    return buyingOrder
  }
  
  def matchSingleOrder(buyingOrder: BuyingOrder, sellingOrder: SellingOrder, transactionPrice: Double) = {
    System.out.println("matched "+buyingOrder.agent+ sellingOrder.agent)
        //Can only match up to whichever amount is largest
        val transactionAmount =
          if (buyingOrder.amount >= sellingOrder.amount)
            sellingOrder.amount
          else
            buyingOrder.amount

        var transactionTotal = transactionAmount * transactionPrice

        var seller = sellingOrder.agent
        var buyer = buyingOrder.agent

        buyer.cash -= transactionTotal
        seller.cash += transactionTotal

        seller.goods.put(buyingOrder.goods, seller.goods.get(buyingOrder.goods).getOrElse(0) - transactionAmount)
        buyer.goods.put(buyingOrder.goods, buyer.goods.get(buyingOrder.goods).getOrElse(0) + transactionAmount)

        sellingOrder.amount -= transactionAmount
        buyingOrder.amount -= transactionAmount
  }
}

	
	

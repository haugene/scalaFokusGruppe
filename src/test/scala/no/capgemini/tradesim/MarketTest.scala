package no.capgemini.tradesim

import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

@Test
class MarketTest {

	/**
	 * Add a selling and buying order. Check that they both exist with correct values
	 */
    @Test
    def testBasics() = {
      var market = new Market("Oslo", 100)
      var trader = new Trader(market, 100.0)
      
      var sellingOrder = new SellingOrder(trader, Goods.Beef, 10.0, 5, 1)
      market.addOrder(sellingOrder)
      
      var buyingOrder = new BuyingOrder(trader, Goods.Fish, 20.0, 10, 2)
      market.addOrder(buyingOrder)
      
      assertEquals(1, market.sellingOrders.size)
      var sellingOrderFound = market.sellingOrders(0)
      assertEquals(trader, sellingOrderFound.actor)
      assertEquals(5, sellingOrderFound.amount)
      assertEquals(Goods.Beef, sellingOrderFound.goods)
      assertEquals(10.0, sellingOrderFound.price, 0.001)
      assertEquals(1, sellingOrderFound.validTime)
      
      assertEquals(1, market.buyingOrders.size)
      var buyingOrderFound = market.buyingOrders(0)
      assertEquals(trader, buyingOrderFound.actor)
      assertEquals(10, buyingOrderFound.amount)
      assertEquals(Goods.Fish, buyingOrderFound.goods)
      assertEquals(20.0, buyingOrderFound.price, 0.001)
      assertEquals(2, buyingOrderFound.validTime)

    }
    
	/**
	 * Add a selling order, then a buying order with the same values. 
	 * Check that the trade has been performed.
	 */
    @Test
    def testMatchingSellingBuying() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(market, 100.0)
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(market, 400.0)
      
      var sellingOrder = new SellingOrder(seller, Goods.Grain, 10.0, 5, 1)
      market.addOrder(sellingOrder)
      
      var buyingOrder = new BuyingOrder(buyer, Goods.Grain, 10.0, 5, 1)
      market.addOrder(buyingOrder)
      
      assertEquals(0, market.sellingOrders.size)
      assertEquals(0, market.buyingOrders.size)
      
      assertEquals(seller.cash, 150.0, 0.001)
      assertEquals(buyer.cash, 350.0, 0.001)
      
      assertEquals(15, seller.goods.get(Goods.Grain).get)
      assertEquals(5, buyer.goods.get(Goods.Grain).get)
    }

	/**
	 * Add a buying order, then a selling order with the same values. 
	 * Check that the trade has been performed.
	 */
    @Test
    def testMatchingBuyingSelling() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(market, 100.0)
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(market, 400.0)
      
      var buyingOrder = new BuyingOrder(buyer, Goods.Grain, 10.0, 5, 1)
      market.addOrder(buyingOrder)
      
      var sellingOrder = new SellingOrder(seller, Goods.Grain, 10.0, 5, 1)
      market.addOrder(sellingOrder)
      
      assertEquals(0, market.sellingOrders.size)
      assertEquals(0, market.buyingOrders.size)
      
      assertEquals(seller.cash, 150.0, 0.001)
      assertEquals(buyer.cash, 350.0, 0.001)
      
      assertEquals(15, seller.goods.get(Goods.Grain).get)
      assertEquals(5, buyer.goods.get(Goods.Grain).get)
    }
}
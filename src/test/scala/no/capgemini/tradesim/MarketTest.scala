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
      var trader = new Trader(100.0, "Trader")
      
      var sellingOrder = new SellingOrder(market, trader, Goods.Beef, 10.0, 5, 1, 0)
      market.addOrder(sellingOrder)
      
      var buyingOrder = new BuyingOrder(market, trader, Goods.Fish, 20.0, 10, 2, 0)
      market.addOrder(buyingOrder)
      
      assertEquals(1, market.sellingOrders.size)
      var sellingOrderFound = market.sellingOrders(0)
      assertEquals(trader, sellingOrderFound.agent)
      assertEquals(5, sellingOrderFound.amount)
      assertEquals(Goods.Beef, sellingOrderFound.goods)
      assertEquals(10.0, sellingOrderFound.price, 0.001)
      assertEquals(1, sellingOrderFound.validTime)
      
      assertEquals(1, market.buyingOrders.size)
      var buyingOrderFound = market.buyingOrders(0)
      assertEquals(trader, buyingOrderFound.agent)
      assertEquals(10, buyingOrderFound.amount)
      assertEquals(Goods.Fish, buyingOrderFound.goods)
      assertEquals(20.0, buyingOrderFound.price, 0.001)
      assertEquals(2, buyingOrderFound.validTime)

    }
    
	/**
	 * Add a selling order, then a buying order with the same values. 
	 * Check that the trade has been performed and nothing remains.
	 */
    @Test
    def testMatchingSellingBuying() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(100.0, "seller")
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(400.0, "buyer")
      
      var sellingOrder = new SellingOrder(market, seller, Goods.Grain, 10.0, 5, 1, 0)
      market.addOrder(sellingOrder)
      
      var buyingOrder = new BuyingOrder(market, buyer, Goods.Grain, 10.0, 5, 1, 0)
      market.addOrder(buyingOrder)
      
      assertEquals(0, market.sellingOrders.size)
      assertEquals(0, market.buyingOrders.size)
      
      assertEquals(seller.cash, 150.0, 0.001)
      assertEquals(buyer.cash, 350.0, 0.001)
      
      assertEquals(15, seller.goods.get(Goods.Grain).get)
      assertEquals(5, buyer.goods.get(Goods.Grain).get)
    }

	/**
	 * Add a selling order, then a buying order with a higher price.
	 * Should still use the lower price. 
	 * Check that the trade has been performed and nothing remains.
	 */
    @Test
    def testSellingThenBuyingHigherPrice() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(100.0, "seller")
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(400.0, "buyer")
      
      var sellingOrder = new SellingOrder(market, seller, Goods.Grain, 10.0, 5, 1, 0)
      market.addOrder(sellingOrder)
      
      var buyingOrder = new BuyingOrder(market, buyer, Goods.Grain, 15.0, 5, 1, 0)
      market.addOrder(buyingOrder)
      
      assertEquals(0, market.sellingOrders.size)
      assertEquals(0, market.buyingOrders.size)
      
      assertEquals(seller.cash, 150.0, 0.001)
      assertEquals(buyer.cash, 350.0, 0.001)
      
      assertEquals(15, seller.goods.get(Goods.Grain).get)
      assertEquals(5, buyer.goods.get(Goods.Grain).get)
    }

	/**
	 * Add a selling order, then a buying order with a lower price.
	 * Nothing should happen. 
	 */
    @Test
    def testSellingThenBuyingLowerPrice() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(100.0, "seller")
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(400.0, "buyer")
      
      var sellingOrder = new SellingOrder(market, seller, Goods.Grain, 10.0, 5, 1, 0)
      market.addOrder(sellingOrder)
      
      var buyingOrder = new BuyingOrder(market, buyer, Goods.Grain, 5.0, 5, 1, 0)
      market.addOrder(buyingOrder)
      
      assertEquals(1, market.sellingOrders.size)
      assertEquals(1, market.buyingOrders.size)
      
      assertEquals(seller.cash, 100.0, 0.001)
      assertEquals(buyer.cash, 400.0, 0.001)
      
      assertEquals(20, seller.goods.get(Goods.Grain).get)
      assertEquals(0, buyer.goods.get(Goods.Grain).getOrElse(0))

      var sellingOrderFound = market.sellingOrders(0)
      assertEquals(5, sellingOrderFound.amount)
      assertEquals(10.0, sellingOrderFound.price, 0.001)
      
      var buyingOrderFound = market.buyingOrders(0)
      assertEquals(5, buyingOrderFound.amount)
      assertEquals(5.0, buyingOrderFound.price, 0.001)
    }

	/**
	 * Add a selling order, then a buying order with a lower amount. 
	 * Check that the trade has been performed and that the remains of the selling order remains.
	 */
    @Test
    def testSellingThenBuyingSome() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(100.0, "seller")
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(400.0, "buyer")
      
      var sellingOrder = new SellingOrder(market, seller, Goods.Grain, 10.0, 5, 1, 0)
      market.addOrder(sellingOrder)
      
      var buyingOrder = new BuyingOrder(market, buyer, Goods.Grain, 10.0, 3, 1, 0)
      market.addOrder(buyingOrder)
      
      assertEquals(1, market.sellingOrders.size)
      assertEquals(0, market.buyingOrders.size)
      
      assertEquals(seller.cash, 130.0, 0.001)
      assertEquals(buyer.cash, 370.0, 0.001)
      
      assertEquals(17, seller.goods.get(Goods.Grain).get)
      assertEquals(3, buyer.goods.get(Goods.Grain).get)
      
      var sellingOrderFound = market.sellingOrders(0)
      assertEquals(2, sellingOrderFound.amount)
    }

	/**
	 * Add a selling order, then a buying order with a larger amount. 
	 * Check that the trade has been performed and that the remains of the buying order remains.
	 */
    @Test
    def testSellingThenBuyingTooMuch() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(100.0, "seller")
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(400.0, "buyer")
      
      var sellingOrder = new SellingOrder(market, seller, Goods.Grain, 10.0, 5, 1, 0)
      market.addOrder(sellingOrder)
      
      var buyingOrder = new BuyingOrder(market, buyer, Goods.Grain, 10.0, 7, 1, 0)
      market.addOrder(buyingOrder)
      
      assertEquals(0, market.sellingOrders.size)
      assertEquals(1, market.buyingOrders.size)
      
      assertEquals(seller.cash, 150.0, 0.001)
      assertEquals(buyer.cash, 350.0, 0.001)
      
      assertEquals(15, seller.goods.get(Goods.Grain).get)
      assertEquals(5, buyer.goods.get(Goods.Grain).get)
      
      var buyingOrderFound = market.buyingOrders(0)
      assertEquals(2, buyingOrderFound.amount)
    }

    /**
	 * Add a buying order, then a selling order with the same values. 
	 * Check that the trade has been performed and nothing remains.
	 */
    @Test
    def testMatchingBuyingSelling() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(100.0, "seller")
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(400.0, "buyer")
      
      var buyingOrder = new BuyingOrder(market, buyer, Goods.Grain, 10.0, 5, 1, 0)
      market.addOrder(buyingOrder)
      
      var sellingOrder = new SellingOrder(market, seller, Goods.Grain, 10.0, 5, 1, 0)
      market.addOrder(sellingOrder)
      
      assertEquals(0, market.sellingOrders.size)
      assertEquals(0, market.buyingOrders.size)
      
      assertEquals(seller.cash, 150.0, 0.001)
      assertEquals(buyer.cash, 350.0, 0.001)
      
      assertEquals(15, seller.goods.get(Goods.Grain).get)
      assertEquals(5, buyer.goods.get(Goods.Grain).get)
    }

    /**
	 * Add two buying orders, then a selling order that matches both. 
	 * Check that the trade has been performed and nothing remains.
	 */
    @Test
    def testMatchingTwoBuyingOneSelling() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(100.0, "seller")
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(400.0, "buyer1")
      var buyer2 = new Trader(400.0, "buyer2")
      
      var buyingOrder = new BuyingOrder(market, buyer, Goods.Grain, 10.0, 5, 1, 0)
      var buyingOrder2 = new BuyingOrder(market, buyer2, Goods.Grain, 10.0, 7, 1, 0)
      market.addOrder(buyingOrder)
      market.addOrder(buyingOrder2)
      
      var sellingOrder = new SellingOrder(market, seller, Goods.Grain, 10.0, 12, 1, 0)
      market.addOrder(sellingOrder)
      
      assertEquals(0, market.sellingOrders.size)
      assertEquals(0, market.buyingOrders.size)
      
      assertEquals(220.0, seller.cash, 0.001)
      assertEquals(350.0, buyer.cash, 0.001)
      assertEquals(330.0, buyer2.cash, 0.001)
      
      assertEquals(8, seller.goods.get(Goods.Grain).get)
      assertEquals(5, buyer.goods.get(Goods.Grain).get)
      assertEquals(7, buyer2.goods.get(Goods.Grain).get)
    }

    /**
	 * Add two buying orders, then a selling order that matches the first and half the second. 
	 * Check that the trade has been performed and only half the second buying order remains.
	 */
    @Test
    def testTwoBuyingOneSellingPartiallyMatching() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(100.0, "seller")
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(400.0, "buyer1")
      var buyer2 = new Trader(400.0, "buyer2")
      
      var buyingOrder = new BuyingOrder(market, buyer, Goods.Grain, 10.0, 5, 1, 0)
      var buyingOrder2 = new BuyingOrder(market, buyer2, Goods.Grain, 10.0, 10, 1, 0)
      market.addOrder(buyingOrder)
      market.addOrder(buyingOrder2)
      
      var sellingOrder = new SellingOrder(market, seller, Goods.Grain, 10.0, 10, 1, 0)
      market.addOrder(sellingOrder)
      
      assertEquals(0, market.sellingOrders.size)
      assertEquals(1, market.buyingOrders.size)
      assertEquals(5, market.buyingOrders(0).amount)
      
      assertEquals(200.0, seller.cash, 0.001)
      assertEquals(350.0, buyer.cash, 0.001)
      assertEquals(350.0, buyer2.cash, 0.001)
      
      assertEquals(10, seller.goods.get(Goods.Grain).get)
      assertEquals(5, buyer.goods.get(Goods.Grain).get)
      assertEquals(5, buyer2.goods.get(Goods.Grain).get)
    }
    /**
	 * Add a buying order, then a selling order with a lower price. 
	 * Check that the trade has been performed at the higher price.
	 */
    @Test
    def testBuyingThenSellingLowerPrice() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(100.0, "seller")
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(400.0, "buyer")
      
      var buyingOrder = new BuyingOrder(market, buyer, Goods.Grain, 10.0, 5, 1, 0)
      market.addOrder(buyingOrder)
      
      var sellingOrder = new SellingOrder(market, seller, Goods.Grain, 8.0, 5, 1, 0)
      market.addOrder(sellingOrder)
      
      assertEquals(0, market.sellingOrders.size)
      assertEquals(0, market.buyingOrders.size)
      
      assertEquals(seller.cash, 150.0, 0.001)
      assertEquals(buyer.cash, 350.0, 0.001)
      
      assertEquals(15, seller.goods.get(Goods.Grain).get)
      assertEquals(5, buyer.goods.get(Goods.Grain).get)
    }

    /**
	 * Add a buying order, then a selling order with a higher price. 
	 * Check that nothing has happened
	 */
    @Test
    def testBuyingThenSellingHigherPrice() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(100.0, "seller")
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(400.0, "buyer")
      
      var buyingOrder = new BuyingOrder(market, buyer, Goods.Grain, 10.0, 5, 1, 0)
      market.addOrder(buyingOrder)
      
      var sellingOrder = new SellingOrder(market, seller, Goods.Grain, 12.0, 5, 1, 0)
      market.addOrder(sellingOrder)
      
      assertEquals(1, market.sellingOrders.size)
      assertEquals(1, market.buyingOrders.size)
      
      assertEquals(seller.cash, 100.0, 0.001)
      assertEquals(buyer.cash, 400.0, 0.001)
      
      assertEquals(20, seller.goods.get(Goods.Grain).get)
      assertEquals(0, buyer.goods.get(Goods.Grain).getOrElse(0))

      var sellingOrderFound = market.sellingOrders(0)
      assertEquals(5, sellingOrderFound.amount)
      assertEquals(12.0, sellingOrderFound.price, 0.001)
      
      var buyingOrderFound = market.buyingOrders(0)
      assertEquals(5, buyingOrderFound.amount)
      assertEquals(10.0, buyingOrderFound.price, 0.001)
    }

    /**
	 * Add a buying order, then a selling order with a lower amount. 
	 * Check that the trade has been performed and nothing remains.
	 */
    @Test
    def testBuyingThenSellingSome() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(100.0, "seller")
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(400.0, "buyer")
      
      var buyingOrder = new BuyingOrder(market, buyer, Goods.Grain, 10.0, 5, 1, 0)
      market.addOrder(buyingOrder)
      
      var sellingOrder = new SellingOrder(market, seller, Goods.Grain, 10.0, 3, 1, 0)
      market.addOrder(sellingOrder)
      
      assertEquals(0, market.sellingOrders.size)
      assertEquals(1, market.buyingOrders.size)
      
      assertEquals(seller.cash, 130.0, 0.001)
      assertEquals(buyer.cash, 370.0, 0.001)
      
      assertEquals(17, seller.goods.get(Goods.Grain).get)
      assertEquals(3, buyer.goods.get(Goods.Grain).get)

      var buyingOrderFound = market.buyingOrders(0)
      assertEquals(2, buyingOrderFound.amount)
    }

    /**
	 * Add a buying order, then a selling order with a larger amount. 
	 * Check that the trade has been performed and nothing remains.
	 */
    @Test
    def testBuyingThenSellingTooMuch() = {
      var market = new Market("Oslo", 100)
      var seller = new Trader(100.0, "seller")
      seller.goods.put(Goods.Grain, 20)
      var buyer = new Trader(400.0, "buyer")
      
      var buyingOrder = new BuyingOrder(market, buyer, Goods.Grain, 10.0, 5, 1, 0)
      market.addOrder(buyingOrder)
      
      var sellingOrder = new SellingOrder(market, seller, Goods.Grain, 10.0, 7, 1, 0)
      market.addOrder(sellingOrder)
      
      assertEquals(1, market.sellingOrders.size)
      assertEquals(0, market.buyingOrders.size)
      
      assertEquals(seller.cash, 150.0, 0.001)
      assertEquals(buyer.cash, 350.0, 0.001)
      
      assertEquals(15, seller.goods.get(Goods.Grain).get)
      assertEquals(5, buyer.goods.get(Goods.Grain).get)

      var sellingOrderFound = market.sellingOrders(0)
      assertEquals(2, sellingOrderFound.amount)
    }

}
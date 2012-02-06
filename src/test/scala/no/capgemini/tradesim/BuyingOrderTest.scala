package no.capgemini.tradesim

import scala.collection.immutable.TreeMap

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@Test
class BuyingOrderTest {

	/**
	 * Add a selling and buying order. Check that they both exist with correct values
	 */
    @Test
    def testCompare() = {
      var market = new Market("Oslo", 100)
      var trader = new Trader(100.0, "trader")
      
      var buyingOrderFourth = new BuyingOrder(market, trader, Goods.Beef, 10.0, 5, 1, 3)
      var buyingOrderFirst = new BuyingOrder(market, trader, Goods.Beef, 11.0, 5, 1, 1)
      var buyingOrderThird = new BuyingOrder(market, trader, Goods.Beef, 10.0, 5, 1, 0)
      var buyingOrderSecond = new BuyingOrder(market, trader, Goods.Beef, 11.0, 5, 1, 2)
      
      var map = new TreeMap[BuyingOrder, BuyingOrder]();
      map+= buyingOrderFourth -> buyingOrderFourth
      map+= buyingOrderFirst -> buyingOrderFirst
      map+= buyingOrderThird -> buyingOrderThird
      map+= buyingOrderSecond -> buyingOrderSecond
   
      var iterator = map.iterator
      assertTrue(iterator.hasNext)
      var first = iterator.next()._1
      assertTrue(iterator.hasNext)
      var second = iterator.next()._1
      assertTrue(iterator.hasNext)
      var third = iterator.next()._1
      assertTrue(iterator.hasNext)
      var fourth = iterator.next()._1
      
      assertEquals(buyingOrderFirst.price, first.price, 0.001)
      assertEquals(buyingOrderFirst.placedTime, first.placedTime)
      assertEquals(buyingOrderSecond.price, second.price, 0.001)
      assertEquals(buyingOrderSecond.placedTime, second.placedTime)
      assertEquals(buyingOrderThird.price, third.price, 0.001)
      assertEquals(buyingOrderThird.placedTime, third.placedTime)
      assertEquals(buyingOrderFourth.price, fourth.price, 0.001)
      assertEquals(buyingOrderFourth.placedTime, fourth.placedTime)
   }
}
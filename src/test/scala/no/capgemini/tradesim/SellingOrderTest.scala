package no.capgemini.tradesim

import scala.collection.immutable.TreeMap

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@Test
class SellingOrderTest {

	/**
	 * Add a selling and buying order. Check that they both exist with correct values
	 */
    @Test
    def testCompare() = {
      var market = new Market("Oslo", 100)
      var trader = new Trader(100.0, "trader")
      
      var sellingOrderFourth = new SellingOrder(market, trader, Goods.Beef, 10.0, 5, 1, 3)
      var sellingOrderFirst = new SellingOrder(market, trader, Goods.Beef, 9.0, 5, 1, 1)
      var sellingOrderThird = new SellingOrder(market, trader, Goods.Beef, 10.0, 5, 1, 0)
      var sellingOrderSecond = new SellingOrder(market, trader, Goods.Beef, 9.0, 5, 1, 2)
      
      var map = new TreeMap[SellingOrder, SellingOrder]();
      map+= sellingOrderFourth -> sellingOrderFourth
      map+= sellingOrderFirst -> sellingOrderFirst
      map+= sellingOrderThird -> sellingOrderThird
      map+= sellingOrderSecond -> sellingOrderSecond
   
      var iterator = map.iterator
      assertTrue(iterator.hasNext)
      var first = iterator.next()._1
      assertTrue(iterator.hasNext)
      var second = iterator.next()._1
      assertTrue(iterator.hasNext)
      var third = iterator.next()._1
      assertTrue(iterator.hasNext)
      var fourth = iterator.next()._1
      
      assertEquals(sellingOrderFirst.price, first.price, 0.001)
      assertEquals(sellingOrderFirst.placedTime, first.placedTime)
      assertEquals(sellingOrderSecond.price, second.price, 0.001)
      assertEquals(sellingOrderSecond.placedTime, second.placedTime)
      assertEquals(sellingOrderThird.price, third.price, 0.001)
      assertEquals(sellingOrderThird.placedTime, third.placedTime)
      assertEquals(sellingOrderFourth.price, fourth.price, 0.001)
      assertEquals(sellingOrderFourth.placedTime, fourth.placedTime)
   }
}
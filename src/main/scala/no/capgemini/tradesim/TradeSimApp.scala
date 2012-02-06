package no.capgemini.tradesim
import scala.util.Random
import scala.collection.mutable.ListBuffer


object App {
  
  /*
   * The main method, will instantiate Markets(just one in the beginning).
   */
  def main(args : Array[String]) {
    val oslo = new Market("Oslo", 600);
    val bergen = new Market("Bergen", 300);
    val trondheim = new Market("Trondheim", 250);
    val stavanger = new Market("Stavanger", 150);
    
    var markets = Map((oslo, oslo), (bergen, bergen), (trondheim, trondheim), (stavanger, stavanger));
    
    oslo.producers += new Producer(oslo, 0, "Oslo Potatoes", Goods.Potato, 5)
    bergen.producers += new Producer(bergen, 0, "Bergen Fish", Goods.Fish, 10)
    trondheim.producers += new Producer(trondheim, 0, "Trondheim Grain", Goods.Grain, 10)
    stavanger.producers += new Producer(stavanger, 0, "Stavanger Beef", Goods.Beef, 10)
    
    oslo.consumers += new Consumer(oslo, 100, "Oslo consumers")
    bergen.consumers += new Consumer(bergen, 100, "Bergen consumers")
    trondheim.consumers += new Consumer(trondheim, 100, "Trondheim consumers")
    stavanger.consumers += new Consumer(stavanger, 100, "Stavanger consumers")
    
    var agents = ListBuffer[Agent](new Trader (100.0, "Trader 1"))
    
    val iterationLimit =
      if (!args.isEmpty) args(0).toInt
      else 100

   	val world = new World(markets, agents)
    world.run(iterationLimit)
    agents.foreach(agent => {
      System.out.println(agent.name+" wealth: "+agent.cash);
    })
    
  }
  
  

}

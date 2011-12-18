package no.capgemini.tradesim
import scala.util.Random

object App {
  
  /*
   * The main method, will instantiate Markets(just one in the beginning).
   */
  def main(args : Array[String]) {
    
    val firstMarket = new Market
    var markets = List(firstMarket)
    
    var actors = List(
      new Consumer(firstMarket, Goods.Grain, 5),
      new Consumer(firstMarket, Goods.Potato, 10),
      new Producer(firstMarket, Goods.Grain, 5),
      new Producer(firstMarket, Goods.Potato, 10));
    
    actors= Random.shuffle(actors);
    
    var iteration = 0;
    
    val iterationLimit =
      if (!args.isEmpty) args(0).toInt
      else 100

    while (iteration < iterationLimit) {
   	 iteration += 1;
   	 
   	 /*
   	  * TODO: For each actor, add order to actor's market
   	  */
   	 println("Iteration "+iteration+":")
   	 
    }
  }

}

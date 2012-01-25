package no.capgemini.tradesim
import scala.util.Random


object App {
  
  /*
   * The main method, will instantiate Markets(just one in the beginning).
   */
  def main(args : Array[String]) {
    
    val oslo = new Market("Oslo", 60);
    val bergen = new Market("Bergen", 30);
    val trondheim = new Market("Trondheim", 25);
    val stavanger = new Market("Stavanger", 15);

    var markets = List(oslo, bergen, trondheim, stavanger);
    
    var actors = List(
      new Producer(oslo, 0, Goods.Potato, 5),
      new Producer(bergen, 0, Goods.Fish, 10),
      new Producer(trondheim, 0, Goods.Grain, 10),
      new Producer(stavanger, 0, Goods.Beef, 10));
    
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

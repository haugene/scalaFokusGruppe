package no.capgemini.tradesim

object App {
  
  /*
   * The main method, will instantiate Markets(just one in the beginning).
   */
  def main(args : Array[String]) {
    
    val firstMarket = new Market
    var markets = List(firstMarket)
    
    var actors = List(
      new Consumer(firstMarket),
      new Consumer(firstMarket),
      new Producer(firstMarket, Goods.Grain),
      new Producer(firstMarket, Goods.Potato));
    
    
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

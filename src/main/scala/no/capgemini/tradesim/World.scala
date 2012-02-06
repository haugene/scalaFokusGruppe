package no.capgemini.tradesim
import scala.util.Random
import scala.collection.immutable.Map
import scala.collection.mutable.ListBuffer

object World {
  var currentWorld : World = null
}

class World (var markets : Map[Market, Market], var agents : ListBuffer[Agent]){
  var currentTurn = 0
  def run(turns : Int) = {
    World.currentWorld = this
    val untilTurn = currentTurn + turns
    while (currentTurn < turns) {
	    produce(currentTurn)
	    runAgents(currentTurn)
	    consume(currentTurn)
	    currentTurn+=1
    }
    
    
  }
  
  def produce(currentTurn: Int) {
    markets.foreach(marketTuple => {
      marketTuple._2.producers.foreach(producer => {
        placeOrders(producer.createOrders(currentTurn))
      })
      System.out.println(marketTuple._2.name+ " current selling orders "+marketTuple._2.sellingOrders.size);
    })
    
  }
  
  def runAgents(currentTurn: Int) {
    agents= Random.shuffle(agents);
    agents.foreach(agent => {
      placeOrders(agent.createOrders(currentTurn))
    })

  }
  
  def consume(currentTurn: Int) {
    markets.foreach(marketTuple => {
      marketTuple._2.consumers.foreach(consumer => {
        placeOrders(consumer.createOrders(currentTurn))
      })
    })
    
    
  }
  
  def placeOrders(orders: ListBuffer[Order]) {
    orders.foreach(order => {
      val market = markets.get(order.market).get
      market.addOrder(order)
    })
  }
 


}
package no.capgemini.tradesim

class Producer(produces : Goods.Goods) extends Actor {

  println(produces.toString());
	
}
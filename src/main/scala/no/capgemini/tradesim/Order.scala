package no.capgemini.tradesim

class Order(
  val actor: Actor,
  val goods: Goods.Goods,
  val price: Double,
  var amount: Int,
  var validTime: Int) {
  

}
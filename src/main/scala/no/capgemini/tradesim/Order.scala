package no.capgemini.tradesim

class Order(
  val market: Market,
  val agent: Agent,
  val goods: Goods.Goods,
  val price: Double,
  var amount: Int,
  var validTime: Int,
  var placedTime: Int) {
  

}
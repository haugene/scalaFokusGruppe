package no.capgemini.tradesim

class BuyingOrder(actor: Actor, goods: Goods.Goods, price: Double, amount: Int, validTime: Int) extends Order(actor, goods, price, amount, validTime) {

}
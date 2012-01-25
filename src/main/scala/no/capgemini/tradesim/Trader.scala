package no.capgemini.tradesim

class Trader(market: Market, cash: Double) extends Actor(market, cash) {

  def createOrder(): Order = { null }

}
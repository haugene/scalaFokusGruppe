package no.capgemini.tradesim

abstract class Actor(val market : Market) {
	def createOrder(): Order
}
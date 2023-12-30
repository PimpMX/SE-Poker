package de.htwg.model.gameFieldComponent

trait CardSetInterface {
    def initialize: CardSetInterface
    def shuffle: CardSetInterface
    def getAvailableCards: Vector[CardInterface]
    def getTakenCards: Vector[CardInterface]
    def take(amount: Int): (Vector[CardInterface], CardSetInterface) 
}

trait CardSetFactoryInterface {
  def apply(): CardSetInterface
}

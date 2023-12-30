package de.htwg.model.gameFieldComponent

trait CardSetInterface {
    def initialize: CardSetInterface
    def shuffle: CardSetInterface
    def getAvailableCards: Vector[CardInterface]
    def getTakenCards: Vector[CardInterface]
    def takeCard(amount: Int): (Vector[CardInterface], CardSetInterface) 
    def takeCommunityCard(amount: Int): (Vector[CommunityCardInterface], CardSetInterface)
}

trait CardSetFactoryInterface {
  def apply(): CardSetInterface
}

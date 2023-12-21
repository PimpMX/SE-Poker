package de.htwg.model.gameFieldComponent

trait CommunityCardsInterface {
    def getCards: Vector[CommunityCardInterface]
    def revealNext: CommunityCardsInterface
    def toString(): String
    def equals(obj: Any): Boolean
}

trait CommunityCardsFactoryInterface {
    def apply(cards: Vector[CommunityCardInterface]): CommunityCardsInterface
}
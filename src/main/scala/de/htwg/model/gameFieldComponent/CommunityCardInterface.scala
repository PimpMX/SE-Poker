package de.htwg.model.gameFieldComponent

trait CommunityCardInterface extends CardInterface {
    def reveal: CommunityCardInterface
    def isRevealed: Boolean
}

trait CommunityCardFactoryInterface {
    def apply(color: Color, rank: Rank, revealed: Boolean): CommunityCardInterface
}
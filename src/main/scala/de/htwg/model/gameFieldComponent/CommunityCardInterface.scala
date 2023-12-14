package de.htwg.model.gameFieldComponent

trait CommunityCardInterface extends CardInterface {
    def reveal: CommunityCardInterface
    def isRevealed: Boolean
}
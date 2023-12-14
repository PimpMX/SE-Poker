package de.htwg.model.gameFieldComponent

trait GameFieldInterface {

    def getPlayers: Vector[PlayerInterface]
    def getNumPlayers: Int
    def getCommunityCards: CommunityCardsInterface
    def switchToNextPlayer: GameFieldInterface
    def getPlayerAtTurn: PlayerInterface
    def activePlayerBet(amount: Int): Option[GameFieldInterface]
    def activePlayerAllIn(): Option[GameFieldInterface]
    def activePlayerCheck(): Option[GameFieldInterface]
    def activePlayerFold(): Option[GameFieldInterface]
    def toString(): String
}
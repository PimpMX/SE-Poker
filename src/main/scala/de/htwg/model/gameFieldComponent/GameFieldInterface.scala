package de.htwg.model.gameFieldComponent

sealed trait BettingRound
case object PREFLOP extends BettingRound
case object FLOP extends BettingRound
case object TURN extends BettingRound
case object RIVER extends BettingRound
case object DONE extends BettingRound

trait GameFieldInterface {

    def getPlayers: Vector[PlayerInterface]
    def getNumPlayers: Int
    def getBettingRound: BettingRound
    def getCommunityCards: CommunityCardsInterface
    def switchToNextPlayer: GameFieldInterface
    def getPlayerAtTurn: PlayerInterface
    def activePlayerBet(amount: Int): Option[GameFieldInterface]
    def activePlayerAllIn(): Option[GameFieldInterface]
    def activePlayerCheck(): Option[GameFieldInterface]
    def activePlayerFold(): Option[GameFieldInterface]
    def toString(): String
}

trait GameFieldFactoryInterface {
    def apply(players: Vector[PlayerInterface], comCards: CommunityCardsInterface, playerAtTurn: Int): GameFieldInterface
}
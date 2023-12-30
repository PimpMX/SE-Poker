package de.htwg.model.gameFieldComponent.gameFieldBaseImpl

import de.htwg.model.gameFieldComponent._
import scala.annotation.switch
import de.htwg.model._

case class GameField(players: Vector[PlayerInterface],
                comCards: CommunityCardsInterface,
                playerAtTurn: Int = 0,
                lastBetPlayerIdx: Int = 0,
                bigBlindPlayerIdx: Int = 0,
                bettingRound: BettingRound = PREFLOP,
                viewStrategy: ViewStrategy = new CLIViewStrategy) extends GameFieldInterface {
  
  def getPlayers: Vector[PlayerInterface] = players
  def getCommunityCards: CommunityCardsInterface = comCards
  def getNumPlayers: Int = players.length
  def getBettingRound: BettingRound = bettingRound

  def switchToNextPlayer: GameFieldInterface = {

    // println("Last bet player: " + this.lastBetPlayerIdx)
    // println("Big Blind Index: " + this.bigBlindPlayerIdx)
    // println("Player at turn: " + this.playerAtTurn)
    // println("betting Round" + this.bettingRound)

    val nextPlayer = (playerAtTurn + 1) % players.length
    GameField(players, comCards, nextPlayer)
  }

  def getNextRound: BettingRound = {
    bettingRound match {
      case PREFLOP => FLOP
      case FLOP => TURN
      case TURN => RIVER
      case RIVER => DONE
      case DONE => DONE
    }
  }

  def getPlayerAtTurn: PlayerInterface = {
    players(playerAtTurn)
  }

  def activePlayerBet(amount: Int): Option[GameFieldInterface] = {

    val betted = this.getPlayerAtTurn.betMoney(amount)

    if (betted.isDefined) {

      val updated = players.updated(playerAtTurn, betted.get)

      val gameField = GameField(
        updated, 
        this.comCards,
        this.playerAtTurn,
        this.playerAtTurn,
        this.bigBlindPlayerIdx,
        if(this.playerAtTurn == this.lastBetPlayerIdx)
          this.getNextRound 
        else this.bettingRound,
      )
      
      Option(gameField.switchToNextPlayer)
    } else {
      Option.empty
    }
  }

  def activePlayerAllIn(): Option[GameFieldInterface] = {

    val playerAtTurn = this.getPlayerAtTurn

    if(playerAtTurn.getBalance <= 0) {
      Option.empty
    } else {
      this.activePlayerBet(playerAtTurn.getBalance)
    } 
  }

  def activePlayerCheck(): Option[GameFieldInterface] = {

    val gameField = GameField(
        this.players,
        this.comCards,
        this.playerAtTurn,
        this.lastBetPlayerIdx,
        this.bigBlindPlayerIdx,
        this.bettingRound
    )

    Option(gameField.switchToNextPlayer)
  }

  def activePlayerFold(): Option[GameFieldInterface] = {

    val folded = this.getPlayerAtTurn.fold

    if(folded.isDefined) {
      val updated = players.updated(playerAtTurn, folded.get)

      val gameField = GameField(
        updated,
        this.comCards,
        this.playerAtTurn,
        this.lastBetPlayerIdx,
        this.bigBlindPlayerIdx,
        this.bettingRound
      )
      
      Option(gameField.switchToNextPlayer)
    } else {
      Option.empty
    }
  }

  override def toString(): String = viewStrategy.produceView(this)
}

class GameFieldFactory extends GameFieldFactoryInterface {
  override def apply(players: Vector[PlayerInterface], comCards: CommunityCardsInterface, playerAtTurn: Int): GameFieldInterface = new GameField(players, comCards, playerAtTurn)
}

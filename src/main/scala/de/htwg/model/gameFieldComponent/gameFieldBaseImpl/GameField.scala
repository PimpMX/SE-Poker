package de.htwg.model.gameFieldComponent.gameFieldBaseImpl

import de.htwg.model.gameFieldComponent._
import scala.annotation.switch
import de.htwg.model._

case class GameField(players: Vector[PlayerInterface],
                comCards: CommunityCardsInterface,
                playerAtTurn: Int = 0,
                lastRaisePlayerIdx: Int = 0,
                bigBlindPlayerIdx: Int = 0,
                bettingRound: BettingRound = PREFLOP,
                highestBet: Int = 0,
                viewStrategy: ViewStrategy = new CLIViewStrategy) extends GameFieldInterface {
  
  def getPlayers: Vector[PlayerInterface] = players
  def getCommunityCards: CommunityCardsInterface = comCards
  def getNumPlayers: Int = players.length
  def getBettingRound: BettingRound = bettingRound
  def getLastRaisePlayerIdx: Int = lastRaisePlayerIdx
  def getBigBlindPlayerIdx: Int = bigBlindPlayerIdx
  def getHighestBet: Int = highestBet

  def switchToNextPlayer: GameFieldInterface = {

    val nextPlayer = (playerAtTurn + 1) % players.length
    val highestBet = players.map(_.getMoneyInPool).max
    val isNextRound = nextPlayer == this.lastRaisePlayerIdx 
    val bettingRound = if(isNextRound) this.getNextRound else this.bettingRound
   
    val nextComCards = if(isNextRound && bettingRound == FLOP)
        this.comCards.revealNext.revealNext.revealNext
      else if(isNextRound && bettingRound != DONE)
        this.comCards.revealNext
      else this.comCards

    printf("Player %d is now at turn\n", nextPlayer)
    printf("LastRaisePlayerIdx: %d\n", lastRaisePlayerIdx)
    printf("BigBlindPlayerIdx: %d\n", bigBlindPlayerIdx)
    printf("BettingRound: %s\n", bettingRound)
    printf("HighestBet: %d\n", players.map(_.getMoneyInPool).max)

    GameField(players,
      nextComCards,
      nextPlayer,
      this.lastRaisePlayerIdx,
      this.bigBlindPlayerIdx,
      bettingRound,
      highestBet)
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

    if(this.getPlayerAtTurn.getMoneyInPool + amount < this.highestBet) {
      return Option.empty
    }

    val betted = this.getPlayerAtTurn.betMoney(amount)

    if (betted.isDefined) {

      val lastRaisePlayerIdx = if(betted.get.getMoneyInPool > highestBet)
        playerAtTurn else this.lastRaisePlayerIdx

      val updated = players.updated(playerAtTurn, betted.get)

      val gameField = GameField(
        updated, 
        this.comCards,
        this.playerAtTurn,
        lastRaisePlayerIdx,
        this.bigBlindPlayerIdx,
        this.bettingRound,
        this.highestBet
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

    if(this.getPlayerAtTurn.getMoneyInPool < this.highestBet) {
      return Option.empty
    } else {
      return Option(this.switchToNextPlayer)
    }
  }

  def activePlayerFold(): Option[GameFieldInterface] = {

    val folded = this.getPlayerAtTurn.fold

    if(folded.isDefined) {
      val updated = players.updated(playerAtTurn, folded.get)

      val gameField = GameField(
        updated,
        this.comCards,
        this.playerAtTurn,
        this.lastRaisePlayerIdx,
        this.bigBlindPlayerIdx,
        this.bettingRound,
        this.highestBet
      )
      
      Option(gameField.switchToNextPlayer)
    } else {
      Option.empty
    }
  }

  override def toString(): String = viewStrategy.produceView(this)
}

class GameFieldFactory extends GameFieldFactoryInterface {
  def apply(players: Vector[PlayerInterface],
    comCards: CommunityCardsInterface,
    playerAtTurn: Int,
    lastRaisePlayerIdx: Int,
    bigBlindPlayerIdx: Int,
    bettingRound: BettingRound,
    highestBet: Int): GameFieldInterface = new GameField(players, comCards, playerAtTurn, lastRaisePlayerIdx, bigBlindPlayerIdx, bettingRound, highestBet)
}


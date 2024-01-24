package de.htwg.model.gameFieldComponent.gameFieldBaseImpl

import de.htwg.model.gameFieldComponent._
import scala.annotation.switch
import de.htwg.model._
import com.google.inject.Guice
import de.htwg.TexasHoldEmModule

case class GameField(players: Vector[PlayerInterface],
                comCards: CommunityCardsInterface,
                playerAtTurn: Int = 0,
                lastRaisePlayerIdx: Int = 0,
                bigBlindPlayerIdx: Int = 0,
                bettingRound: BettingRound = PREFLOP,
                highestBet: Int = 0,
                viewStrategy: ViewStrategy = new CLIViewStrategy) extends GameFieldInterface {
                  
  //  Determines the amount for the big blind and small blind
    
  val BIG_BLIND_AMOUNT = 50
  val SMALL_BLIND_AMOUNT = 25

  val injector = Guice.createInjector(new TexasHoldEmModule)
  val cardSetFactory = injector.getInstance(classOf[CardSetFactoryInterface])
  val communityCardsFactory = injector.getInstance(classOf[CommunityCardsFactoryInterface])
  val playerFactory = injector.getInstance(classOf[PlayerFactoryInterface])
  val cardEvaluator = injector.getInstance(classOf[CardEvaluatorInterface])
  
  def startOfRound: GameFieldInterface = {

    //  Filter out players that dont have enough money to play
    //  (Simplified because player may be able to pay the small blind)

    var updatedPlayers = this.players.filter(_.getBalance >= BIG_BLIND_AMOUNT)

    if(updatedPlayers.length == 1) {

      //  If theres only one player left, the game is over

      GameField(updatedPlayers,
        this.comCards,
        this.playerAtTurn,
        this.lastRaisePlayerIdx,
        this.bigBlindPlayerIdx,
        GAME_FINISHED,
        this.highestBet)
    
    } else {

      val newBigBlindPlayerIdx = (this.bigBlindPlayerIdx + 1) % updatedPlayers.length
      val smallBlindIdx = if (newBigBlindPlayerIdx - 1 < 0)
          newBigBlindPlayerIdx - 1 + this.players.length
        else
          newBigBlindPlayerIdx - 1

      updatedPlayers = updatedPlayers.map { player =>

        if(player.getPlayerNum == newBigBlindPlayerIdx) {
          player.betMoney(BIG_BLIND_AMOUNT).get
        } else if(player.getPlayerNum == smallBlindIdx) {
          player.betMoney(SMALL_BLIND_AMOUNT).get
        } else {
          player
        }
      }

      val newPlayerAtTurn = (newBigBlindPlayerIdx + 1) % updatedPlayers.length

      GameField(updatedPlayers,
        this.comCards,
        newPlayerAtTurn,
        newBigBlindPlayerIdx,
        newBigBlindPlayerIdx,
        PREFLOP,
        BIG_BLIND_AMOUNT)
    }
  }

  def endOfRound: GameFieldInterface = {

    //  Determine pool money and reset players for next round

    val poolMoney = players.map(_.getMoneyInPool).sum
    var updatedPlayers = players.map(player => 
      playerFactory(player.getPlayerNum, player.getHand, player.getBalance, 0, false)
    )

    //  Determine the winner

    val winnerIdx = if(this.getPlayersLeftInRound.length == 1) {
      this.getPlayersLeftInRound(0).getPlayerNum
    } else {

      // Call the Card Evaluator

      val cardEvaluations = cardEvaluator.rankCards(this.getPlayersLeftInRound, this.comCards)
      val winningHand = cardEvaluations.head

      print(winningHand.player.getPlayerStr + " wins with " + winningHand.handRank.toString() + "\n")

      winningHand.player.getPlayerNum
    }

    //  Give the winner the pool money

    updatedPlayers = updatedPlayers.map { player =>

      if(player.getPlayerNum == winnerIdx) {

        playerFactory(player.getPlayerNum,
          player.getHand,
          player.getBalance + poolMoney,
          player.getMoneyInPool,
          player.getFoldedStatus)
      
      } else {
        player
      }
    }

    GameField(updatedPlayers,
      this.comCards,
      this.playerAtTurn,
      this.lastRaisePlayerIdx,
      this.bigBlindPlayerIdx,
      this.bettingRound,
      this.highestBet).dealNewCards.startOfRound
  }

  def getNextRound: BettingRound = {
    bettingRound match {
      case PREFLOP => FLOP
      case FLOP => TURN
      case TURN => RIVER
      case RIVER => SHOWDOWN
      case SHOWDOWN => PREFLOP
      case GAME_FINISHED => GAME_FINISHED
    }
  }

  def getPlayers: Vector[PlayerInterface] = players
  def getCommunityCards: CommunityCardsInterface = comCards
  def getNumPlayers: Int = players.length
  def getBettingRound: BettingRound = bettingRound
  def getLastRaisePlayerIdx: Int = lastRaisePlayerIdx
  def getBigBlindPlayerIdx: Int = bigBlindPlayerIdx
  def getHighestBet: Int = highestBet
  def getMoneyInPool: Int = players.map(player => player.getMoneyInPool).sum

  def switchToNextPlayer: GameFieldInterface = {

    //  First we check if the round is over, the round is either over
    //  when theres only one player left (everyone else folded) or when
    //  theres nobody left to bet (everyone is all in)

    if(this.getPlayersLeftInRound.length == 1 || !this.playersAreStillBetting) {

      //  Round is over and should be evaluated

      return GameField(this.players,
        this.comCards,
        this.playerAtTurn,
        this.lastRaisePlayerIdx,
        this.bigBlindPlayerIdx,
        SHOWDOWN,
        this.highestBet)
    }

    //  Evaluate the next player, since there is more than one
    //  player left there should be another player at turn

    var nextPlayer = (playerAtTurn + 1) % players.length

    //  We want to skip all players that folded

    while(players(nextPlayer).getFoldedStatus || players(nextPlayer).isAllIn) {
      nextPlayer = (nextPlayer + 1) % players.length
    }

    val highestBet = players.map(_.getMoneyInPool).max
    val isNextRound = nextPlayer == this.lastRaisePlayerIdx 
    val bettingRound = if(isNextRound) this.getNextRound else this.bettingRound

    val nextComCards = if(isNextRound && bettingRound == FLOP)
        this.comCards.revealNext.revealNext.revealNext
      else if(isNextRound && bettingRound != SHOWDOWN)
        this.comCards.revealNext
      else this.comCards

    GameField(players,
      nextComCards,
      nextPlayer,
      this.lastRaisePlayerIdx,
      this.bigBlindPlayerIdx,
      bettingRound,
      highestBet)
  }

  def dealNewCards: GameField = {

    var cardSet = cardSetFactory().initialize.shuffle

    //  Give every player a new hand

    val updatedPlayers: Vector[PlayerInterface] = this.players.map { player =>
      
      val (hand, newCardSet) = cardSet.takeCard(2)
      cardSet = newCardSet

      // Return new player object with new cards

      playerFactory(player.getPlayerNum,
        Hand(hand(0), hand(1)),
        player.getBalance,
        player.getMoneyInPool,
        player.getFoldedStatus)
    }

    val newComCards = communityCardsFactory(cardSet.takeCommunityCard(5)._1)

    GameField(updatedPlayers,
      newComCards,
      this.playerAtTurn,
      this.lastRaisePlayerIdx,
      this.bigBlindPlayerIdx,
      this.bettingRound,
      this.highestBet)
  }

  def getPlayerAtTurn: PlayerInterface = {
    players(playerAtTurn)
  }

  // Returns amount of players still in round (didn't fold)

  def getPlayersLeftInRound: Vector[PlayerInterface] = {
    players.filter(player => { !player.getFoldedStatus })
  }

  // To check if there are still players that can bet

  def playersAreStillBetting: Boolean = {
    players.exists(player => { !player.getFoldedStatus && !player.isAllIn })
  }

  def activePlayerBet(amount: Int): Option[GameFieldInterface] = {

    if(this.bettingRound == SHOWDOWN) {
      return Option(this.endOfRound)
    } else if(this.bettingRound == GAME_FINISHED) {
      return Option.empty
    }

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

    if(this.bettingRound == SHOWDOWN) {
      return Option(this.endOfRound)
    } else if(this.bettingRound == GAME_FINISHED) {
      return Option.empty
    }

    val betted = this.getPlayerAtTurn.betMoney(this.getPlayerAtTurn.getBalance)

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

  def activePlayerCheck(): Option[GameFieldInterface] = {

    if(this.bettingRound == SHOWDOWN) {
      return Option(this.endOfRound)
    } else if(this.bettingRound == GAME_FINISHED) {
      return Option.empty
    }

    if(this.getPlayerAtTurn.getMoneyInPool < this.highestBet) {
      return Option.empty
    } else {
      return Option(this.switchToNextPlayer)
    }
  }

  def activePlayerFold(): Option[GameFieldInterface] = {

    if(this.bettingRound == SHOWDOWN) {
      return Option(this.endOfRound)
    } else if(this.bettingRound == GAME_FINISHED) {
      return Option.empty
    }

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


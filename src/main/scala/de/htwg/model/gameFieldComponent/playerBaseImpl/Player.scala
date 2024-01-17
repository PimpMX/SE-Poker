package de.htwg.model.gameFieldComponent.playerBaseImpl

import scala.util.Try
import de.htwg.model.gameFieldComponent.PlayerInterface
import de.htwg.model.gameFieldComponent.Hand
import de.htwg.model.gameFieldComponent.PlayerBuilderInterface
import scala.compiletime.ops.boolean

case class Player(playerNum: Int,
            hand: Hand,
            balance: Int,
            moneyInPool: Int,
            hasFolded: Boolean = false) extends PlayerInterface {

  def getPlayerNum: Int = playerNum
  def getBalance: Int = balance
  def getMoneyInPool: Int = moneyInPool
  def getHand: Hand = hand
  def getFoldedStatus: Boolean = hasFolded

  def getPlayerStr: String = s"Player${playerNum}"
  def getBalanceStr: String = s"${balance}"
  def getBettedStr: String = s"${moneyInPool}"
  
  def betMoney(amount: Int): Option[Player] = {

    if (amount <= balance && hasFolded == false) {
      Option(Player(playerNum, hand, balance - amount, moneyInPool + amount, hasFolded))
    } else {
      Option.empty
    }
  }

  def fold: Option[Player] = {
    if(hasFolded) {
      Option.empty
    } else {
      Option(Player(playerNum, hand, balance, moneyInPool, true))
    }
  }

  def setHand(hand: Hand): PlayerInterface = {
    Player(playerNum, hand, balance, moneyInPool, hasFolded)
  }

  def setMoneyInPool(amount: Int): PlayerInterface = {
    Player(playerNum, hand, balance, amount, hasFolded)
  }

  def setBalance(amount: Int): PlayerInterface = {
    Player(playerNum, hand, amount, moneyInPool, hasFolded)
  }

  def setFolded(hasFolded: Boolean): PlayerInterface = {
    Player(playerNum, hand, balance, moneyInPool, hasFolded)
  }
}

class PlayerBuilder extends PlayerBuilderInterface {
  private var playerNum: Int = _
  private var hand: Hand = _
  private var balance: Int = _
  private var moneyInPool: Int = _
  private var hasFolded: Boolean = false

  def setPlayerNum(playerNum: Int): PlayerBuilder = {
    this.playerNum = playerNum
    this
  }

  def setHand(hand: Hand): PlayerBuilder = {
    this.hand = hand
    this
  }

  def setBalance(balance: Int): PlayerBuilder = {
    this.balance = balance
    this
  }

  def setMoneyInPool(moneyInPool: Int): PlayerBuilder = {
    this.moneyInPool = moneyInPool
    this
  }

  def setFoldedStatus(foldedStatus: Boolean): PlayerBuilder = {
    this.hasFolded = foldedStatus
    this
  }

  def build(): Player = {
    Player(playerNum, hand, balance, moneyInPool, hasFolded)
  }
}

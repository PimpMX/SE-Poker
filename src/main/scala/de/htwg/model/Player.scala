package de.htwg.model

import scala.util.Try

case class Player(playerNum: Int,
            hand: Hand,
            balance: Int,
            moneyInPool: Int,
            hasFolded: Boolean = false) {

  def getPlayerStr: String = s"Player${playerNum}"
  def getBalanceStr: String = s"${balance}"
  def getBettedStr: String = s"${moneyInPool}"

  def betMoney(amount: Int): Option[Player] = {
    if (amount <= balance) {
      Option(Player(playerNum, hand, balance - amount, moneyInPool + amount))
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
}

class PlayerBuilder {
  private var playerNum: Int = _
  private var hand: Hand = _
  private var balance: Int = _
  private var moneyInPool: Int = _

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

  def build(): Player = {
    Player(playerNum, hand, balance, moneyInPool)
  }
}

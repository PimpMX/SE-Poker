package de.htwg.model

import scala.util.Try

class Player(playerNum: Int,
             hand: Hand,
             balance: Int,
             moneyInPool: Int) {

  def getPlayerNum: Int = playerNum
  def getHand: Hand = hand
  def getBalance: Int = balance
  def getBettedMoney: Int = moneyInPool

  def getPlayerStr: String = s"Player${playerNum}"
  def getBalanceStr: String = s"${balance}"
  def getBettedStr: String = s"${moneyInPool}"

  def betMoney(amount: Int): Option[Player] = {
    if (amount <= balance) {
      Option(new Player(playerNum, hand, balance - amount, moneyInPool + amount))
    } else {
      Option.empty
    }
  }
}

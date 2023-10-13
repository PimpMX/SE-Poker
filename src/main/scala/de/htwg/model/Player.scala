package de.htwg.model

import scala.util.Try

class Player(argPlayerNum: Int, argCards: Array[Card], argBalance: Int, argMoneyInPool: Int) {

  //  Vektor anstatt Array verwenden.

  private val playerNum: Int = argPlayerNum
  private val cards: Array[Card] = argCards

  private val balance: Int = argBalance
  private val moneyInPool: Int = 0

  def getPlayerNum: Int = argPlayerNum
  def getCards: Array[Card] = argCards
  def getBalance: Int = balance
  def betMoney(amount: Int): Option[Player] = {
    if (amount <= balance) {
      Option(new Player(playerNum, cards, balance - amount, moneyInPool + amount))
    } else {
      Option.empty
    }
  }
}

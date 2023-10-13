package de.htwg.model

import scala.util.Try

class Player(playerNum: Int,
             cards: Array[Card],
             balance: Int,
             moneyInPool: Int) {

  //  Vektor anstatt Array verwenden.

  /*
  def getPlayerNum: Int = argPlayerNum
  def getCards: Array[Card] = argCards
  def getBalance: Int = balance
  */

  def betMoney(amount: Int): Option[Player] = {
    if (amount <= balance) {
      Option(new Player(playerNum, cards, balance - amount, moneyInPool + amount))
    } else {
      Option.empty
    }
  }
}

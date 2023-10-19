package de.htwg.model

import scala.util.Try

class Player(playerNum: Int,
             hand: Hand,
             balance: Int,
             moneyInPool: Int) {

  //  Vektor anstatt Array verwenden.

  def getPlayerNum: Int = playerNum
  def getHand: Hand = hand;
  def getBalance: Int = balance;
  def getBettedMoney: Int = balance;

  def betMoney(amount: Int): Option[Player] = {
    if (amount <= balance) {
      Option(new Player(playerNum, hand, balance - amount, moneyInPool + amount))
    } else {
      Option.empty
    }
  }
}

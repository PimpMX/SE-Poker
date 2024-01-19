package de.htwg.model.gameFieldComponent.playerBaseImpl

import scala.util.Try
import de.htwg.model.gameFieldComponent.PlayerInterface
import de.htwg.model.gameFieldComponent.Hand
import scala.compiletime.ops.boolean
import de.htwg.model.gameFieldComponent.PlayerFactoryInterface

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
}

class PlayerFactory extends PlayerFactoryInterface  {
  def apply(playerNum: Int, hand: Hand, balance: Int, moneyInPool: Int, foldedStatus: Boolean): Player = {
    Player(playerNum, hand, balance, moneyInPool, foldedStatus)
  }
}

package de.htwg.model.gameFieldComponent

case class Hand(cards: (CardInterface, CardInterface)) {

  override def toString: String = s"${cards._1}${cards._2}"
}

trait PlayerInterface {

    def getPlayerNum: Int
    def getBalance: Int
    def getMoneyInPool: Int
    def getHand: Hand
    def getFoldedStatus: Boolean
    def getPlayerStr: String
    def getBalanceStr: String
    def getBettedStr: String
    
    def isAllIn: Boolean
    def betMoney(amount: Int): Option[PlayerInterface]
    def fold: Option[PlayerInterface]
}

trait PlayerFactoryInterface {
    def apply(playerNum: Int, hand: Hand, balance: Int, moneyInPool: Int, foldedStatus: Boolean): PlayerInterface
}
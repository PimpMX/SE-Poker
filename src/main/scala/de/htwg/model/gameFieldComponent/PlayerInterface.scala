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
    
    def betMoney(amount: Int): Option[PlayerInterface]
    def fold: Option[PlayerInterface]

    // TODO: Get rid of this workaround and use factory/builder in the GameField class

    def setHand(hand: Hand): PlayerInterface
    def setMoneyInPool(amount: Int): PlayerInterface
    def setBalance(amount: Int): PlayerInterface
    def setFolded(hasFolded: Boolean): PlayerInterface
}

trait PlayerBuilderInterface {

    def setPlayerNum(playerNum: Int): PlayerBuilderInterface
    def setHand(hand: Hand): PlayerBuilderInterface
    def setBalance(balance: Int): PlayerBuilderInterface
    def setMoneyInPool(moneyInPool: Int): PlayerBuilderInterface
    def setFoldedStatus(foldedStatus: Boolean): PlayerBuilderInterface
    def build(): PlayerInterface
}
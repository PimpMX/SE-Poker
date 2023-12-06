package de.htwg.model

import scala.annotation.switch

case class GameField private(players: Vector[Player],
                comCards: CommunityCards,
                playerAtTurn: Int = 0,
                viewStrategy: ViewStrategy = CLIViewStrategy()) {

  def getNumPlayers: Int = players.length
  def getCommunityCards: CommunityCards = comCards

  def switchToNextPlayer: GameField = {
    val nextPlayer = (playerAtTurn + 1) % players.length
    GameField(players, comCards, nextPlayer)
  }

  def getPlayerAtTurn: Player = {
    players(playerAtTurn)
  }

  def activePlayerBet(amount: Int): Option[GameField] = {

    val betted = this.getPlayerAtTurn.betMoney(amount)

    if (betted.isDefined) {
      val updated = players.updated(playerAtTurn, betted.get)
      val gameField = GameField(updated, comCards, this.playerAtTurn)
      Option(gameField.switchToNextPlayer)
    } else {
      Option.empty
    }
  }

  def activePlayerAllIn(): Option[GameField] = {

    val playerAtTurn = this.getPlayerAtTurn

    if(playerAtTurn.balance <= 0) {
      Option.empty
    } else {
      this.activePlayerBet(playerAtTurn.balance)
    } 
  }

  def activePlayerCheck(): Option[GameField] = {
    Option(switchToNextPlayer)
  }

  def activePlayerFold(): Option[GameField] = {

    val folded = this.getPlayerAtTurn.fold

    if(folded.isDefined) {
      val updated = players.updated(playerAtTurn, folded.get)
      val gameField = GameField(updated, comCards, this.playerAtTurn)
      Option(gameField.switchToNextPlayer)
    } else {
      Option.empty
    }
  }

  override def toString(): String = viewStrategy.produceView(this)
}

object GameField {

  def apply(numPlayers: Int): Option[GameField] = {

    if(numPlayers <= 0 || numPlayers > 10) {
      Option.empty
    } else {

      val playerBuilder = new PlayerBuilder()

      val players: Vector[Player] = (0 until numPlayers).map { i =>
        playerBuilder.setPlayerNum(i)
          .setHand(Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))))
          .setBalance(1000)
          .setMoneyInPool(0)
          .build()
      }.toVector
  
      val comCard: Vector[CommunityCard] = Vector.fill(5)(new CommunityCard(CLUBS, ACE, false))
      val comCardO: CommunityCards = new CommunityCards(comCard)
  
      Option(GameField(players, comCardO))
    }
  }
}

abstract class ViewStrategy {
    def produceView(gameField: GameField): String
}

class CLIViewStrategy extends ViewStrategy {

  def produceView(gameState: GameField): String = {

    val calculated = calcFieldLen(gameState)
    val fieldLen = if(calculated > 25) { calculated } else { 25 }
    
    val (topUsers, botUsers) = gameState.players.splitAt(
      if (gameState.players.length > 1)
        gameState.players.length / 2 
      else gameState.players.length % 2
    )

    val topUserNames: String = (for (user <- topUsers) yield s"${if(user.hasFolded == false) user.getPlayerStr else "FOLDED "}    ").mkString("")
    val topUserCards: String = (for (user <- topUsers) yield s"${if(gameState.playerAtTurn == user.playerNum) user.hand else "[**][**]"}   ").mkString("")
    val topUserBalance: String = (for (user <- topUsers) yield f"${user.getBettedStr}%-11s").mkString("")

    val botUserNames: String = (for (user <- botUsers) yield s"${if(user.hasFolded == false) user.getPlayerStr else "FOLDED "}    ").mkString("")
    val botUserCards: String = (for (user <- botUsers) yield s"${if(gameState.playerAtTurn == user.playerNum) user.hand else "[**][**]"}   ").mkString("")
    val botUserBalance: String = (for (user <- botUsers) yield f"${user.getBettedStr}%-11s").mkString("")

    val comCards: CommunityCards = gameState.getCommunityCards
    val paddedComCards: String = centerString(fieldLen, comCards.toString())

    val maxNameLen = math.max(botUserNames.length(), topUserNames.length())

    if(gameState.getNumPlayers > 2) {

      val outString: String = "*" * fieldLen + "\n" +
        s"*   ${topUserNames.padTo(maxNameLen, " ").mkString("")}*\n" +
        s"*   ${topUserCards.padTo(maxNameLen, " ").mkString("")}*\n" +
        s"*   ${topUserBalance.padTo(maxNameLen, " ").mkString("")}*\n" +
        "*" + " " * (fieldLen - 2)  + "*\n" +
        s"*${paddedComCards}*\n" +
        "*" + " " * (fieldLen - 2)  + "*\n" +
        s"*   ${botUserBalance.padTo(maxNameLen, " ").mkString("")}*\n" +
        s"*   ${botUserCards.padTo(maxNameLen, " ").mkString("")}*\n" +
        s"*   ${botUserNames.padTo(maxNameLen, " ").mkString("")}*\n" +
        "*" * fieldLen

      outString
    } else {

      val outString: String = "*" * fieldLen + "\n" +
        s"*${centerString(fieldLen, topUserNames)}*\n" +
        s"*${centerString(fieldLen, topUserCards)}*\n" +
        s"*${centerString(fieldLen, topUserBalance)}*\n" +
        "*" + " " * (fieldLen - 2)  + "*\n" +
        s"*${paddedComCards}*\n" +
        "*" + " " * (fieldLen - 2)  + "*\n" +
        s"*${centerString(fieldLen, botUserBalance)}*\n" +
        s"*${centerString(fieldLen, botUserCards)}*\n" +
        s"*${centerString(fieldLen, botUserNames)}*\n" +
        "*" * fieldLen

      outString
    }
  }

  def calcFieldLen(gameState: GameField): Int = {
    5 + 11 * math.max(gameState.getNumPlayers / 2,
            gameState.getNumPlayers - gameState.getNumPlayers / 2)
  }

  def centerString(fieldLen: Int, str: String): String = {
    val padding: Int = ((fieldLen - 2 - str.length()) / 2)
    val paddedComCards: String = " " * padding + str + " " * (fieldLen - 2 - padding - str.length)
    paddedComCards
  }
}

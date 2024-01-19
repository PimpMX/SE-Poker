package de.htwg.model.gameFieldComponent.gameFieldBaseImpl

import de.htwg.model.gameFieldComponent._

abstract class ViewStrategy {
    def produceView(gameField: GameFieldInterface): String
}

class CLIViewStrategy extends ViewStrategy {

  def produceView(gameState: GameFieldInterface): String = {

    val calculated = calcFieldLen(gameState)
    val fieldLen = if(calculated > 25) { calculated } else { 25 }
    
    val (topUsers, botUsers) = gameState.getPlayers.splitAt(
      if (gameState.getNumPlayers > 1)
        gameState.getNumPlayers  / 2 
      else gameState.getNumPlayers % 2
    )

    val topUserNames: String = (for (user <- topUsers) yield s"${
        if(user.getFoldedStatus == false)
          user.getPlayerStr
        else "FOLDED "}    ").mkString("")

    val topUserCards: String = (for (user <- topUsers) yield s"${
      if(gameState.getPlayerAtTurn == user || (gameState.getBettingRound == SHOWDOWN && user.getFoldedStatus == false))
        user.getHand
      else "[**][**]"}   ").mkString("")

    val topUserBalance: String = (for (user <- topUsers) yield f"${user.getBettedStr}%-11s").mkString("")

    val botUserNames: String = (for (user <- botUsers.reverse) yield s"${
      if(user.getFoldedStatus == false)
        user.getPlayerStr
      else "FOLDED "}    ").mkString("")

    val botUserCards: String = (for (user <- botUsers.reverse) yield s"${
      if(gameState.getPlayerAtTurn == user || (gameState.getBettingRound == SHOWDOWN && user.getFoldedStatus == false))
        user.getHand
      else "[**][**]"}   ").mkString("")

    val botUserBalance: String = (for (user <- botUsers.reverse) yield f"${user.getBettedStr}%-11s").mkString("")

    val comCards: CommunityCardsInterface = gameState.getCommunityCards
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

  def calcFieldLen(gameState: GameFieldInterface): Int = {
    5 + 11 * math.max(gameState.getNumPlayers / 2,
            gameState.getNumPlayers - gameState.getNumPlayers / 2)
  }

  def centerString(fieldLen: Int, str: String): String = {
    val padding: Int = ((fieldLen - 2 - str.length()) / 2)
    val paddedComCards: String = " " * padding + str + " " * (fieldLen - 2 - padding - str.length)
    paddedComCards
  }
}
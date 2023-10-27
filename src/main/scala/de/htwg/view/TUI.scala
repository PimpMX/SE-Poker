package de.htwg.view

import de.htwg.model.GameField
import de.htwg.model.CommunityCard
import de.htwg.model.CommunityCards

object TUI {

  def produceCLIView(gameState: GameField): String = {

    val calculated = calcFieldLen(gameState)
    val fieldLen = if(calculated > 25) { calculated } else { 25 }
    
    val (topUsers, botUsers) = gameState.getPlayers.splitAt(
      if (gameState.getPlayers.length > 1)
        gameState.getPlayers.length / 2 
      else gameState.getPlayers.length % 2
    )

    val topUserNames: String = (for (user <- topUsers) yield s"${user.getPlayerStr}    ").mkString("")
    val topUserCards: String = (for (user <- topUsers) yield s"${user.getHand}   ").mkString("")
    val topUserBalance: String = (for (user <- topUsers) yield f"${user.getBettedStr}%-11s").mkString("")

    val botUserNames: String = (for (user <- botUsers) yield s"${user.getPlayerStr}    ").mkString("")
    val botUserCards: String = (for (user <- botUsers) yield s"${user.getHand}   ").mkString("")
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
        "*" * fieldLen + "\n"

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
        "*" * fieldLen + "\n"

      outString
    }
  }

  def calcFieldLen(gameState: GameField): Int = {
    5 + 11 * math.max(gameState.getNumPlayers / 2,
            gameState.getNumPlayers - gameState.getNumPlayers / 2)
  }

  def centerString(fieldLen: Int, str: String): String = {

    val padding: Int = ((fieldLen - 2 - str.length()) / 2)
    val paddedComCards: String = " " * padding 
                        + str.toString 
                        + " " * (fieldLen - 2 - padding - str.toString().length)

    paddedComCards
  }
}

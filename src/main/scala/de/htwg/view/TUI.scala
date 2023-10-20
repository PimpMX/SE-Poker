package de.htwg.view

import de.htwg.model.GameField

object TUI {

  //  Kriegt stand jetzt noch keine Daten aber gibt zurück wie das Spielfeld aussehen soll.
  def produceCLIView(gameState: GameField): String = {

    val calculated = calcFieldLen(gameState)
    val fieldLen = if(calculated > 25) { calculated } else { 25 }
    val (topUsers, botUsers) = gameState.getPlayers.splitAt(gameState.getPlayers.length / 2)

    val topUserNames: Vector[String] = for (user <- topUsers) yield s"${user.getPlayerStr}    "
    val topUserCards: Vector[String] = for (user <- topUsers) yield s"${user.getHand}   "
    val topUserBalance: Vector[String] = for (user <- topUsers) yield f"${user.getBettedStr}%-11s"

    val botUserNames: Vector[String] = for (user <- botUsers) yield s"${user.getPlayerStr}    "
    val botUserCards: Vector[String] = for (user <- botUsers) yield s"${user.getHand}   "
    val botUserBalance: Vector[String] = for (user <- botUsers) yield f"${user.getBettedStr}%-11s"

    println("*" * fieldLen)
    println(s"*   ${topUserNames.mkString("")}   *")
    println(s"*   ${topUserCards.mkString("")}   *")
    println(s"*   ${topUserBalance.mkString("")}   *")

    println("")
    println("")

    println(botUserBalance.mkString(""))
    println(botUserCards.mkString(""))
    println(botUserNames.mkString(""))
    println("*" * fieldLen)


    val outString = s""

    /*
    val outString: String = """
        |           Welcome to SE-Poker!
        |
        |*************************************************
        |*   Player1    Player2    Player3    Player4    *
        |*   [PA][KK]   [**][**]   [**][**]   [**][**]   *
        |*   1000       1000       1000       1000       *
        |*                                               *
        |*             [K6][PA][**][**][**]              *
        |*                                               *
        |*   1000       1000       1000       1000       *
        |*   [**][**]   [**][**]   [**][**]   [**][**]   *
        |*   Player5    Player6    Player7    Player8    *
        |*************************************************
        |""".stripMargin
    */

    outString
  }

  def calcFieldLen(gameState: GameField): Int = {
    8 + 8 * (gameState.getNumPlayers / 2) + ((gameState.getNumPlayers - 1) / 2) * 3
  }
}

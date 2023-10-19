package de.htwg.view

import de.htwg.model.GameField

object TUI {

  //  Kriegt stand jetzt noch keine Daten aber gibt zurück wie das Spielfeld aussehen soll.
  def produceCLIView(gameState: GameField): String = {

    val calculated = calcFieldLen(gameState)
    val fieldLen = if(calculated > 25) { calculated } else { 25 }
    val (topUsers, botUsers) = gameState.getPlayers.splitAt(gameState.getPlayers.length / 2)

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

    outString
  }

  def calcFieldLen(gameState: GameField): Int = {
    8 + 8 * gameState.getNumPlayers + (gameState.getNumPlayers - 1) * 3
  }
}

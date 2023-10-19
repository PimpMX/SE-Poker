package de.htwg.view

import de.htwg.model.GameField

object TUI {

  //  Kriegt stand jetzt noch keine Daten aber gibt zurück wie das Spielfeld aussehen soll.
  def produceCLIView(gameState: GameField): String = {

    val calculated = calcFieldLen(gameState)
    val fieldLen = if(calculated > 25) { calculated } else { 25 }
    val (topUsers, botUsers) = gameState.getPlayers.splitAt(gameState.getPlayers.length / 2)

    print(topUsers + " " + botUsers)

    val outString: String = "*" * calculated;

    //outString = outString + "\n"

    outString
  }

  def calcFieldLen(gameState: GameField): Int = {
    8 + 8 * gameState.getNumPlayers + (gameState.getNumPlayers - 1) * 3
  }
}

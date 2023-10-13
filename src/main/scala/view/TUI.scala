package view

import model.GameField

object TUI {

  //  Kriegt stand jetzt noch keine Daten aber gibt zurück wie das Spielfeld aussehen soll.
  def produceCLIView(gameState: GameField): String = {

    val calculated = calcFieldLen(gameState)
    val fLength = if(calculated > 25) { calculated } else { 25 }

    print(calculated)

    //  Skalierung mit Mock Daten machen.

    val outString: String = """
        |           Welcome to SE-Poker!
        |
        |*************************** *************************
        |*   [PA][KK]   [**][**]   [**][**]   [**][**]   *
        |*                                                   *
        |*   [K6][PA][**][**][**]                         *
        |*                                                  *
        |*   [**][**]   [**][**]   [**][**]   [**][**]    *
        |***************************************************
        |""".stripMargin

    outString
  }

  def calcFieldLen(gameState: GameField): Int = {
    8 + 8 * gameState.getNumPlayers + (gameState.getNumPlayers - 1) * 3
  }
}

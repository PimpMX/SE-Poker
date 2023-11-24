package de.htwg

import de.htwg.controller.Controller
import de.htwg.model.GameField
import de.htwg.view.TUI

object TexasHoldEm {

  def main(args: Array[String]): Unit = {

    val controller = new Controller(GameField.gameFactory(4).get)
    val tui = new TUI(controller)
    tui.gameLoop()
  }
}

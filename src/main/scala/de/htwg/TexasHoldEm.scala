package de.htwg

import de.htwg.model.GameHandler
import de.htwg.controller.Controller
import de.htwg.view.TUI

object TexasHoldEm {

  def main(args: Array[String]): Unit = {

    val controller = new Controller(GameHandler.generateThreePlayerGame())
    val tui = new TUI(controller)
    tui.gameLoop()
  }
}

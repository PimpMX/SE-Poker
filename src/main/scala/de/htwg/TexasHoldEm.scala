package de.htwg

import de.htwg.controller.Controller
import de.htwg.model.GameHandler.getCardSet

object TexasHoldEm {

  @main
  def run(): Unit = {
    println(Controller.getCLIView)
    getCardSet()
  }
}

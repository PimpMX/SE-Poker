package de.htwg.controller

import de.htwg.view.TUI
import de.htwg.model.{GameField, GameHandler}
object Controller {

  def getCLIView: String = {
    TUI.produceCLIView(GameHandler.initGame(2))
  }
}
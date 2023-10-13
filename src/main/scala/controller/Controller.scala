package controller

import view.TUI
object Controller {

  def getCLIView: String = {
    TUI.produceCLIView(model.GameHandler.initGame(2))
  }
}
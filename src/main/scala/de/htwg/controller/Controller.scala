package de.htwg.controller

import de.htwg.view.TUI
import de.htwg.model.{GameField, GameHandler}
object Controller {

  def getCLIView: String = {
    TUI.produceCLIView(GameHandler.initGame(6))
  }

  def userCmd(input: String): String = {
    input match {
      case "new game" => return "entered new game"
      case bet if bet.startsWith("bet ") && bet.substring(4).forall(_.isDigit) => return "entered bet"
      case "bet all-in" => return "entered bet all-in"
      case "check" => return "entered check"
      case "fold" => return "folded"
      case "exit" => System.exit(0)
      case _ => return "Invalid input"
    }
    input
  }
}
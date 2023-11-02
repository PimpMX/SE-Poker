package de.htwg.controller

import de.htwg.view.TUI
import de.htwg.model.{GameField, GameHandler}
object Controller {

  def getCLIView(gameState: GameField): String = {
    TUI.produceCLIView(gameState)
  }

  def userCmd(input: String): String = {

    var gameState: GameField = GameHandler.initGame(6)

    input match {
      case "new game" => getCLIView(gameState)
      case bet if bet.startsWith("bet ") && bet.substring(4).forall(_.isDigit) => return "entered bet"
      case "bet all-in" => "entered bet all-in"
      case "check" => 
        gameState = gameState.switchToNextPlayer()
        getCLIView(gameState)
      case "fold" => "folded"
      case _ => "Invalid input"
    }
  }
}
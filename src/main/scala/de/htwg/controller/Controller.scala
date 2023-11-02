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
      case "new game" => 
        println("new game started")
        getCLIView(gameState)
      case bet if bet.startsWith("bet ") && bet.substring(4).forall(_.isDigit) => 
        println("entered bet")
        gameState = gameState.switchToNextPlayer()
        getCLIView(gameState)
      case "bet all-in" => 
        println("entered bet all-in")
        gameState = gameState.switchToNextPlayer()
        getCLIView(gameState)
      case "check" => 
        println("checked")
        gameState = gameState.switchToNextPlayer()
        getCLIView(gameState)
      case "fold" => 
        println("folded")
        gameState = gameState.switchToNextPlayer()
        getCLIView(gameState)
      case _ => "Invalid input"
    }
  }
}
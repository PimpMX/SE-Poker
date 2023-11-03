package de.htwg.controller

import de.htwg.view.TUI
import de.htwg.model.{GameField, GameHandler}
object Controller {

  def getCLIView(gameState: GameField): String = {
    TUI.produceCLIView(gameState)
  }

  // def gameLoop(): Unit = {

  //   var gameState: Option[GameField] = Some(GameHandler.generateThreePlayerGame())

  //   while(true) {
  //     val input = scala.io.StdIn.readLine()
  //     gameState = userCmd(input, gameState.get)
      
  //     if(gameState.isDefined)
  //       println(getCLIView(gameState.get))
  //     else 
  //       System.exit(0)
  //   }
  // }

  def userCmd(input: String, gameState: GameField): Option[GameField] = {

    input match {
      case "new game" => 
        Some(GameHandler.generateThreePlayerGame())
      case bet if bet.startsWith("bet ") && bet.substring(4).forall(_.isDigit) => 
        Some(gameState.switchToNextPlayer())
      case "bet all-in" =>
        Some(gameState.switchToNextPlayer())
      case "check" => 
        Some(gameState.switchToNextPlayer())
      case "fold" => 
        Some(gameState.switchToNextPlayer())
      case _ =>
        None
    }
  }
}
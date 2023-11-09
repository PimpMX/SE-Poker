package de.htwg

import de.htwg.controller.Controller
import de.htwg.model.CommunityCard
import de.htwg.model.GameHandler

object TexasHoldEm {

  def main(args: Array[String]): Unit = {
    
    val expectedTwoPlayersNoTurn_STR = 
     """|*************************
        |*      Player0          *
        |*      [PA][CA]         *
        |*      0                *
        |*                       *
        |* [**][**][**][**][**]  *
        |*                       *
        |*      0                *
        |*      [**][**]         *
        |*      Player1          *
        |*************************
      """.stripMargin
    val expectedTwoPlayerTurn_CTRL = Controller.getCLIView(GameHandler.generateTwoPlayerGame())

    println(expectedTwoPlayerTurn_CTRL)
    println("----------------")
    println(expectedTwoPlayersNoTurn_STR)
    
    // println(Controller.getCLIView(GameHandler.generateThreePlayerGame().switchToNextPlayer()))

    // Controller.gameLoop()
  }
}

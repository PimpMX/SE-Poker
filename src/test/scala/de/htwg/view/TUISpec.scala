package de.htwg.view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.model._

class TUISpec extends AnyWordSpec with Matchers {

    "TUI" should {
        "produce a the correct scalable CLI view of the game state" in {
            val gameStateTwoPlayers: GameField = GameHandler.generateTwoPlayerGame()
            val gameStateThreePlayers: GameField = GameHandler.generateThreePlayerGame()

            val expectedTwoPlayersNoTurn = 
"""*************************
*      Player0          *
*      [PA][CA]         *
*      0                *
*                       *
* [**][**][**][**][**]  *
*                       *
*      0                *
*      [**][**]         *
*      Player1          *
*************************
"""

            val expectedTwoPlayersTurned = 
"""*************************
*      Player0          *
*      [**][**]         *
*      0                *
*                       *
* [**][**][**][**][**]  *
*                       *
*      0                *
*      [PA][CA]         *
*      Player1          *
*************************
"""

            val expectedThreePlayerNoTurn = 
"""***************************
*   Player0               *
*   [PA][CA]              *
*   0                     *
*                         *
*  [**][**][**][**][**]   *
*                         *
*   0          0          *
*   [**][**]   [**][**]   *
*   Player1    Player2    *
***************************
"""

            val expectedThreePlayerTurned =
"""***************************
*   Player0               *
*   [**][**]              *
*   0                     *
*                         *
*  [**][**][**][**][**]   *
*                         *
*   0          0          *
*   [PA][CA]   [**][**]   *
*   Player1    Player2    *
***************************
"""

            TUI.produceCLIView(gameStateTwoPlayers).stripMargin should be(expectedTwoPlayersNoTurn)
            TUI.produceCLIView(gameStateTwoPlayers.switchToNextPlayer()).stripMargin should be(expectedTwoPlayersTurned)
            
            TUI.produceCLIView(gameStateThreePlayers).stripMargin should be(expectedThreePlayerNoTurn)
            TUI.produceCLIView(gameStateThreePlayers.switchToNextPlayer()).stripMargin should be(expectedThreePlayerTurned)
        }
    }
}


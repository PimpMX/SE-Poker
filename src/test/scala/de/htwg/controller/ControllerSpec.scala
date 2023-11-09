package de.htwg.controller

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.model.GameHandler

class ControllerSpec extends AnyWordSpec with Matchers {

    // "Controller" should {

    //     val gameState = GameHandler.generateTwoPlayerGame()

    //     "handle new game command correctly" in {
    //         val newGameState = Controller.userCmd("new game", gameState)
    //         newGameState.isDefined should be(true)
    //         newGameState.get should be (gameState)
    //     }

    //     "handle bet command correctly" in {
    //         val betCommand = "bet 100"
    //         val betGameState = Controller.userCmd(betCommand, gameState)
    //         betGameState.isDefined should be(true)
    //         betGameState.get.playerAtTurn should be(1)
    //         betGameState.get should not be (gameState)
    //     }

    //     "handle bet all-in command correctly" in {
    //         val allInGameState = Controller.userCmd("bet all-in", gameState)
    //         allInGameState.isDefined should be(true)
    //         allInGameState.get.playerAtTurn should be(1)
    //         allInGameState.get should not be (gameState)
    //     }

    //     "handle check command correctly" in {
    //         val checkGameState = Controller.userCmd("check", gameState)
    //         checkGameState.isDefined should be(true)
    //         checkGameState.get.playerAtTurn should be(1)
    //         checkGameState.get should not be (gameState)
    //     }

    //     "handle fold command correctly" in {
    //         val foldGameState = Controller.userCmd("fold", gameState)
    //         foldGameState.isDefined should be(true)
    //         foldGameState.get.playerAtTurn should be(1)
    //         foldGameState.get should not be (gameState)
    //     }
    // }

    // "return None for invalid user commands" in {
    //     val gameState = GameHandler.generateTwoPlayerGame()
    //     val invalidCommand = "invalid command"
    //     val invalidGameState = Controller.userCmd(invalidCommand, gameState)
    //     invalidGameState should be(None)
    // }

    "Controller" should {

        val gameState = GameHandler.generateTwoPlayerGame()
        val controller = new Controller(gameState)

        "handle restart game command correctly" in {
            controller.restart_game()
        }

        "handle bet command correctly" in {
            controller.bet(100)
        }

        "handle bet all-in command correctly" in {
            controller.bet_all_in()
        }

        "handle check command correctly" in {
            controller.check()
        }

        "handle fold command correctly" in {
            controller.fold()
        }

        "return the correct string representation of the game state" in {
            controller.restart_game()
            controller.toString() should be(gameState.toString())
        }
    }
}

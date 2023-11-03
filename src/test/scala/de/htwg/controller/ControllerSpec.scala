package de.htwg.controller

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.model.GameHandler

class ControllerSpec extends AnyWordSpec with Matchers {

    "Controller" should {

        "handle user commands correctly" in {
            val gameState = GameHandler.generateTwoPlayerGame()

            "new game command" should {
                val newGameState = Controller.userCmd("new game", gameState)
                newGameState.isDefined should be(true)
                newGameState.get should be (gameState)
            }

            "bet command" should {
                val betCommand = "bet 100"
                val betGameState = Controller.userCmd(betCommand, gameState)
                betGameState.isDefined should be(true)
                betGameState.get.playerAtTurn should be(1)
                betGameState.get should not be (gameState)
            }

            "bet all-in command" should {
                val allInGameState = Controller.userCmd("bet all-in", gameState)
                allInGameState.isDefined should be(true)
                allInGameState.get.playerAtTurn should be(1)
                allInGameState.get should not be (gameState)
            }

            "check command" should {
                val checkGameState = Controller.userCmd("check", gameState)
                checkGameState.isDefined should be(true)
                checkGameState.get.playerAtTurn should be(1)
                checkGameState.get should not be (gameState)
            }

            "fold command" should {
                val foldGameState = Controller.userCmd("fold", gameState)
                foldGameState.isDefined should be(true)
                foldGameState.get.playerAtTurn should be(1)
                foldGameState.get should not be (gameState)
            }
        }

        "return None for invalid user commands" in {
            val gameState = GameHandler.generateTwoPlayerGame()
            val invalidCommand = "invalid command"
            val invalidGameState = Controller.userCmd(invalidCommand, gameState)
            invalidGameState should be(None)
        }
    }
}

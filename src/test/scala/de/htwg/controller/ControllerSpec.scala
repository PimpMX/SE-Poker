package de.htwg.controller

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.model.gameFieldComponent.gameFieldBaseImpl.GameField
import de.htwg.model.gameFieldComponent.gameFieldGenerator.GameGenerator
import de.htwg.controller.controllerBaseImpl.Controller
import de.htwg.controller.commands.BetAllInCmd
import de.htwg.controller.commands.BetCmd
import de.htwg.controller.commands.CheckCmd
import de.htwg.controller.commands.FoldCmd
import de.htwg.controller.commands.NewGameCmd

class ControllerSpec extends AnyWordSpec with Matchers {

    "Controller" when {

        val controller = new Controller

        "created with 2 players" should {
            
            "return no game for invalid bet" in {
                controller.bet(1100) shouldBe(false)
            }

            "return a game for valid bet" in {
                controller.bet(100) shouldBe(true)
            }

            "return a game for valid bet all-in" in {
                controller.betAllIn() shouldBe(true)
            }

            "return no game for invalid all-in" in {
                controller.check()
                controller.betAllIn() shouldBe(false)
            }

            "return a game for check" in {
                controller.check() shouldBe(true)
            }

            "return a game for fold" in {
                controller.fold() shouldBe(true)
            }

            "return no game for invalid fold" in {
                controller.fold()
                controller.fold() shouldBe(false)
            }

            "be able to render the GameState" in {
                controller.toString() should be(controller.getGameState().toString())
            }

            "undo the last action" in {
                controller.undo()
                controller.toString() should be(controller.getGameState().toString())
            }

            "redo the last action" in {
                controller.redo()
                controller.toString() should be(controller.getGameState().toString())
            }

            "exit the game" in {
                controller.exit()
                controller.toString() should be(controller.getGameState().toString())
            }

            "revert the game state to the previous state on BetAllInCmd" in {
                val command = new BetAllInCmd(controller)

                val initialGameState = controller.getGameState()

                command.doStep
                val changedGameState = controller.getGameState()

                command.undoStep

                val revertedGameState = controller.getGameState()
                revertedGameState should be (initialGameState)
                revertedGameState should not be (changedGameState)
            }

            "revert the game state to the state after the last doStep on BetAllInCmd" in {
                val command = new BetAllInCmd(controller)

                val initialGameState = controller.getGameState()

                command.doStep
                val changedGameState = controller.getGameState()

                command.undoStep

                command.redoStep

                val revertedGameState = controller.getGameState()
                revertedGameState should be (changedGameState)
                revertedGameState should not be (initialGameState)
        }

            "revert the game state to the previous state on BetCmd" in {
                val command = new BetCmd(controller, 500)

                val initialGameState = controller.getGameState()

                command.doStep
                val changedGameState = controller.getGameState()

                command.undoStep

                val revertedGameState = controller.getGameState()
                revertedGameState should be (initialGameState)
                revertedGameState should not be (changedGameState)
            }

            "revert the game state to the state after the last doStep on BetCmd" in {
                val command = new BetCmd(controller, 500)

                val initialGameState = controller.getGameState()

                command.doStep
                val changedGameState = controller.getGameState()

                command.undoStep

                command.redoStep

                val revertedGameState = controller.getGameState()
                revertedGameState should be (changedGameState)
                revertedGameState should not be (initialGameState)
            }

            "revert the game state to the previous state on CheckCmd" in {
                val command = new CheckCmd(controller)

                val initialGameState = controller.getGameState()

                command.doStep
                val changedGameState = controller.getGameState()

                command.undoStep

                val revertedGameState = controller.getGameState()
                revertedGameState should be (initialGameState)
                revertedGameState should not be (changedGameState)
            }

            "revert the game state to the state after the last doStep on CheckCmd" in {
                val command = new CheckCmd(controller)

                val initialGameState = controller.getGameState()

                command.doStep
                val changedGameState = controller.getGameState()

                command.undoStep

                command.redoStep

                val revertedGameState = controller.getGameState()
                revertedGameState should be (changedGameState)
                revertedGameState should not be (initialGameState)
            }

            "revert the game state to the previous state on FoldCmd" in {
                val command = new FoldCmd(controller)

                val initialGameState = controller.getGameState()

                command.doStep
                val changedGameState = controller.getGameState()

                command.undoStep

                val revertedGameState = controller.getGameState()
                revertedGameState should be (initialGameState)
                revertedGameState should not be (changedGameState)
            }

            "revert the game state to the state after the last doStep on FoldCmd" in {
                val command = new FoldCmd(controller)

                val initialGameState = controller.getGameState()

                command.doStep
                val changedGameState = controller.getGameState()

                command.undoStep

                command.redoStep

                val revertedGameState = controller.getGameState()
                revertedGameState should be (changedGameState)
                revertedGameState should not be (initialGameState)
            }

            "revert the game state to the previous state on NewGameCmd" in {
                val command = new NewGameCmd(controller, 2)

                val initialGameState = controller.getGameState()

                command.doStep
                val changedGameState = controller.getGameState()

                command.undoStep

                val revertedGameState = controller.getGameState()
                revertedGameState should be (initialGameState)
                revertedGameState should not be (changedGameState)
            }

            "revert the game state to the state after the last doStep on NewGameCmd" in {
                val command = new NewGameCmd(controller, 2)

                val initialGameState = controller.getGameState()

                command.doStep
                val changedGameState = controller.getGameState()

                command.undoStep

                command.redoStep

                val revertedGameState = controller.getGameState()
                revertedGameState should be (changedGameState)
                revertedGameState should not be (initialGameState)
            }
        }
    }
}

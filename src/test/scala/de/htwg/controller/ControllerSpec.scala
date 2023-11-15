package de.htwg.controller

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.model.GameHandler

class ControllerSpec extends AnyWordSpec with Matchers {

    "Controller" when {

        val gameState = GameHandler.generateGame(2)
        val controller = new Controller(gameState)

        "created with 2 players" should {
            
            "have a valid GameState" in {
                gameState.isDefined shouldBe(true)
            }

            "return no game for invalid bet" in {
                controller.bet(1100) shouldBe(true)
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
                controller.toString() should be(gameState.get.toString())
            }
        }

        "created with 11 players" should {

            val gameState = GameHandler.generateGame(11)

            "return no game" in {
                gameState.isDefined shouldBe(false)
            }
        }
    }

    // "Controller" should {

    //     val gameState = GameHandler.generateTwoPlayerGame()
    //     val controller = new Controller(gameState)

    //     "return new game with valid number" in {
    //         val newGame = controller.newGame(1)
    //         newGame.shouldBe(true)
    //     }

    //     "return no game with invalid number" in {
    //         val newGame = controller.newGame(0)
    //         newGame.shouldBe(false)
    //     }

    //     "handle valid bet command correctly" in {
    //         val bet = controller.bet(100)
    //         bet.shouldBe(true)
    //     }

    //     "handle bet command correctly" in {
    //         controller.bet(100)
    //     }

    //     "handle bet all-in command correctly" in {
    //         controller.bet_all_in()
    //     }

    //     "handle check command correctly" in {
    //         controller.check()
    //     }

    //     "handle fold command correctly" in {
    //         controller.fold()
    //     }

    //     "return the correct string representation of the game state" in {
    //         controller.restart_game()
    //         controller.toString() should be(gameState.toString())
    //     }
    // }
}

package de.htwg.controller

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.model.gameFieldComponent.gameFieldBaseImpl.GameField
import de.htwg.model.gameFieldComponent.gameFieldGenerator.GameGenerator
import de.htwg.controller.controllerBaseImpl.Controller

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
        }
    }
}

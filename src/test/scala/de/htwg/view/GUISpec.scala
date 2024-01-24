package de.htwg.view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.model._
import de.htwg.controller._
import de.htwg.util._
import scala.util.Success
import scala.util.Failure
import de.htwg.view.GUI

import de.htwg.controller.controllerBaseImpl.Controller
import de.htwg.model.gameFieldComponent.gameFieldGenerator.GameGenerator
import java.awt.Dimension

class GUITest extends AnyWordSpec with Matchers {
    "A GUI" when {
        "initialized" should {
            "have correct default values" in {
                val controller = new Controller()
                val gui = new GUI(controller)

                gui.title should be("SE-Poker")
                gui.visible should be(true)
                gui.preferredSize shouldBe new Dimension(800, 512)
                gui.minimumSize should be(new Dimension(200, 400))
            }
        }
    }
}
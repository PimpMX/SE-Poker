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
import scala.util.Try

class GUITest extends AnyWordSpec with Matchers {

    "A GUI" when {

        "initialized" should {
        
            "have correct default values" in {
        
                val controller = new Controller()
                val gui: Try[GUI] = Try(new GUI(controller))

                gui match { 
                    case Success(e) => {
                        gui.get.title should be("SE-Poker")
                        gui.get.visible should be(true)
                        gui.get.preferredSize shouldBe new Dimension(1600, 1080)
                        gui.get.minimumSize should be(new Dimension(1000, 1000))
                    } 
                    case Failure(e) => println("[INFO] Starting without GUI")
                }
            }
        }
    }
}
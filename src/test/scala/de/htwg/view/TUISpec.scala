package de.htwg.view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.model._
import de.htwg.controller._
import de.htwg.util._
import scala.util.Success
import scala.util.Failure

import de.htwg.controller.controllerBaseImpl.Controller
import de.htwg.model.gameFieldComponent.gameFieldGenerator.GameGenerator

class TUISpec extends AnyWordSpec with Matchers {

    "TUI" should {

        "have a working new game command" in {
            val controller = new Controller
            val tui = new TUI(controller)
            tui.userCmd("new game 1") shouldBe a[Success[_]]
            tui.userCmd("new game 11") shouldBe a[Failure[_]]
        }

        "have a working bet command" in {
            val controller = new Controller
            val tui = new TUI(controller)

            tui.userCmd("bet 100") shouldBe a[Success[_]]
            tui.userCmd("bet 10000") shouldBe a[Failure[_]]
        }

        "have a working bet all-in command" in {
            val controller = new Controller
            controller.newGame(1)
            val tui = new TUI(controller)

            tui.userCmd("bet all-in") shouldBe a[Success[_]]
            tui.userCmd("bet all-in") shouldBe a[Failure[_]]
        }

        "have a working check command" in {
            val controller = new Controller
            val tui = new TUI(controller)
            tui.userCmd("check") shouldBe a[Success[_]]
        }

        "have a working fold command" in {
            val controller = new Controller
            val tui = new TUI(controller)
            tui.userCmd("fold") shouldBe a[Success[_]]
        }

        "have a working invalid command" in {
            val controller = new Controller
            val tui = new TUI(controller)
            tui.userCmd("invalid command") shouldBe a[Failure[_]]
        }

        "have a working update method from observer" in {
            val controller = new Controller
            val tui = new TUI(controller)
            tui.update(Move)
        }
    }
}


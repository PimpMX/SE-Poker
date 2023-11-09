package de.htwg.view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.model._
import de.htwg.controller._
import de.htwg.util._

class TUISpec extends AnyWordSpec with Matchers {

    "TUI" when {

        "created with Controller" should {

            val controller = new Controller(GameHandler.generateThreePlayerGame())
            val tui = new TUI(controller)

            "have a working userCmd" in {
                tui.userCmd("bet 100") should be(true)
                tui.userCmd("bet all-in") should be(true)
                tui.userCmd("check") should be(true)
                tui.userCmd("fold") should be(true)
                tui.userCmd("new game") should be(true)
                tui.userCmd("invalid command") should be(false)
            }

            "have a working update" in {
                tui.update(Event.Move)
            }

            "be unregisterable from the controller" in {
                controller.remove(tui)
            }
        }
    }
}


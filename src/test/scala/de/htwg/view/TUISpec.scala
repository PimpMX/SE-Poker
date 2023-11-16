package de.htwg.view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.model._
import de.htwg.controller._
import de.htwg.util._

class TUISpec extends AnyWordSpec with Matchers {

    "TUI" should {

        "have a working new game command" in {
            val controller = new Controller(GameHandler.generateGame(1).get)
            val tui = new TUI(controller)
            tui.userCmd("new game 1") should be((true, Option.empty))
            tui.userCmd("new game 11") should be((false, Option("Number should be in range 1-10")))
        }

        "have a working bet command" in {
            val controller = new Controller(GameHandler.generateGame(1).get)
            val tui = new TUI(controller)

            tui.userCmd("bet 100") should be((true, Option.empty))
            tui.userCmd("bet 10000") should be((false, Option("Not enough money")))
        }

        "have a working bet all-in command" in {
            val controller = new Controller(GameHandler.generateGame(1).get)
            val tui = new TUI(controller)

            tui.userCmd("bet all-in") should be((true, Option.empty))
            tui.userCmd("bet all-in") should be((false, Option("No money left to bet")))
        }

        "have a working check command" in {
            val controller = new Controller(GameHandler.generateGame(1).get)
            val tui = new TUI(controller)
            tui.userCmd("check") should be((true, Option.empty))
        }

        "have a working fold command" in {
            val controller = new Controller(GameHandler.generateGame(1).get)
            val tui = new TUI(controller)
            tui.userCmd("fold") should be((true, Option.empty))
        }

        "have a working invalid command" in {
            val controller = new Controller(GameHandler.generateGame(1).get)
            val tui = new TUI(controller)
            tui.userCmd("invalid command") should be((false, Option("Invalid command")))
        }

        "have a working update method from observer" in {
            val controller = new Controller(GameHandler.generateGame(1).get)
            val tui = new TUI(controller)
            tui.update(Event.Move)
        }
    }
}


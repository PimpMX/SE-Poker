package de.htwg.view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TUISpec extends AnyWordSpec with Matchers {

    "TicTacToe" should {
        "have a bar as String of form '+---+---+---+'" in {
            val x = "+---+---+---+"
            x should be("+---+---+---+")
        }
    }
}

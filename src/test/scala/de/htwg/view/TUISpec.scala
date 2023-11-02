package de.htwg.view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TUISpec extends AnyWordSpec with Matchers {

    "TUI" should {
        "should have" in {
            val x = "+---+---+---+"
            x should be("+---+---+---+")
        }
    }
}

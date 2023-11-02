package de.htwg

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TexasHoldEmSpec extends AnyWordSpec with Matchers{

  "TexasHoldEm Main" when {
    "called" should {
      "return Unit" in {
        assert(TexasHoldEm.main(Array()) ==())
      }
    }
  }
}

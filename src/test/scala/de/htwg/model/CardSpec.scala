package de.htwg.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class CardSpec extends AnyWordSpec with Matchers {
  "A Card" when {
    "created with Color PIP and Rank TWO" should {
      val card = new Card(PIP, TWO)
      "have PIP Color" in {
        card.color should be(PIP)
      }
      "have rank TWO" in {
        card.rank should be(TWO)
      }
      "return false when comparing with a different object" in {
        card.equals(1) should be(false)
      }
      "have a string representation of [P2]" in {
        card.toString should be("[P2]")
      }
    }
  }
}
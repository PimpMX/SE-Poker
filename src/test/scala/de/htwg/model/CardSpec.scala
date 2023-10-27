package de.htwg.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class CardSpec extends AnyWordSpec with Matchers {
  "A Card" when {
    "created with Color PIP and Rank TWO" should {
      val card = Card(Color.PIP, Rank.TWO)
      "have PIP Color" in {
        card.color should be(Color.PIP)
      }
      "have rank TWO" in {
        card.rank should be(Rank.TWO)
      }
      "have a string representation of [P2]" in {
        card.toString should be("[P2]")
      }
    }
  }
}
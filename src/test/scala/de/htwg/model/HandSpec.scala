package de.htwg.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class HandSpec extends AnyWordSpec with Matchers {

  "A Hand" when {
    "created with two cards" should {
      val hand = Hand(Card(Color.PIP, Rank.TWO), Card(Color.PIP, Rank.THREE))
      "have the correct cards" in {
        hand should be(Hand(Card(Color.PIP, Rank.TWO), Card(Color.PIP, Rank.THREE)))
      }

      "have a string representation of [P2][P3]" in {
        hand.toString should be("[P2][P3]")
      }
    }
  }
}
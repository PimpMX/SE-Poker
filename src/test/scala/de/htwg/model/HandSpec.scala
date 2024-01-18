package de.htwg.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import de.htwg.model.gameFieldComponent.cardBaseImpl._
import de.htwg.model.gameFieldComponent.playerBaseImpl._
import de.htwg.model.gameFieldComponent.cardBaseImpl._
import de.htwg.model.gameFieldComponent._

class HandSpec extends AnyWordSpec with Matchers {

  "A Hand" when {
    "created with two cards" should {
      val hand = Hand((new Card(SPADES, TWO), new Card(SPADES, THREE)))
      "have the correct cards" in {
        hand should be((Hand(new Card(SPADES, TWO), new Card(SPADES, THREE))))
      }

      "have a string representation of [P2][P3]" in {
        hand.toString should be("[P2][P3]")
      }
    }
  }
}
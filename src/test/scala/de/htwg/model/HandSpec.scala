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
      val hand = Hand((new Card(DIAMONDS, TWO), new Card(DIAMONDS, THREE)))
      "have the correct cards" in {
        hand should be((Hand(new Card(DIAMONDS, TWO), new Card(DIAMONDS, THREE))))
      }

      "have a string representation of [D2][D3]" in {
        hand.toString should be("[D2][D3]")
      }
    }
  }
}
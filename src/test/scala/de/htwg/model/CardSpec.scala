package de.htwg.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.model.gameFieldComponent.cardBaseImpl.Card
import de.htwg.model.gameFieldComponent._

class CardSpec extends AnyWordSpec with Matchers {
  "A Card" when {
    "created with Color SPADES and Rank TWO" should {
      val card = new Card(SPADES, TWO)
      "have PIP Color" in {
        card.getColor should be(SPADES)
      }
      "have rank TWO" in {
        card.getRank should be(TWO)
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
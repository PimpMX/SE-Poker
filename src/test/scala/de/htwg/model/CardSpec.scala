package de.htwg.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.model.gameFieldComponent.cardBaseImpl.Card
import de.htwg.model.gameFieldComponent._

class CardSpec extends AnyWordSpec with Matchers {
  "A Card" when {
    "created with Color Diamonds and Rank TWO" should {
      val card = new Card(DIAMONDS, TWO)
      "have DIAMONDS Color" in {
        card.getColor should be(DIAMONDS)
      }
      "have rank TWO" in {
        card.getRank should be(TWO)
      }
      "return false when comparing with a different object" in {
        card.equals(1) should be(false)
      }
      "have a string representation of [D2]" in {
        card.toString should be("[D2]")
      }
    } 
  }
}
package de.htwg.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import de.htwg.model.gameFieldComponent.comCardBaseImpl._
import de.htwg.model.gameFieldComponent.comCardsBaseImpl._
import de.htwg.model.gameFieldComponent.cardBaseImpl.Card
import de.htwg.model.gameFieldComponent._

class CommunityCardsSpec extends AnyWordSpec with Matchers  {

  "CommunityCards" when {
    "created with 5 Cards (Clubs - Ace, DIAMONDS - Two, Hearts - Ten, Spades - Eight, DIAMONDS - Five)" should {

      val cards: Vector[CommunityCard] = Vector(
        new CommunityCard(CLUBS, ACE, false),
        new CommunityCard(DIAMONDS, TWO, false),
        new CommunityCard(HEARTS, TEN, false),
        new CommunityCard(SPADES, EIGHT, false),
        new CommunityCard(DIAMONDS, FIVE, false)
      )

      val comCard: CommunityCards = new CommunityCards(cards)

      "return the correct cards" in {
        comCard.getCards should contain theSameElementsAs Vector(
          new CommunityCard(CLUBS, ACE, false),
          new CommunityCard(DIAMONDS, TWO, false),
          new CommunityCard(HEARTS, TEN, false),
          new CommunityCard(SPADES, EIGHT, false),
          new CommunityCard(DIAMONDS, FIVE, false)
        )
      }

      "return false when comparing with a different Object" in {
        comCard.equals(5) should be (false)
      } 

      "return the correct revealNext CommunityCards instance" in {
        comCard.revealNext should be(new CommunityCards(Vector(
          new CommunityCard(CLUBS, ACE, true),
          new CommunityCard(DIAMONDS, TWO, false),
          new CommunityCard(HEARTS, TEN, false),
          new CommunityCard(SPADES, EIGHT, false),
          new CommunityCard(DIAMONDS, FIVE, false)
        )))
      }

      "give the correct toString with all cards unrevealed [**][**][**][**][**]" in {
        comCard.toString() should be ("[**][**][**][**][**]")
      }

      "give the correct toString with one card revealed [CA][**][**][**][**]" in {
        comCard.revealNext.toString() should be ("[CA][**][**][**][**]")
      }

      "give the correct toString with two cards revealed [CA][D2][**][**][**]" in {
        comCard.revealNext.revealNext.toString() should be ("[CA][D2][**][**][**]")
      }
    }
  }
}

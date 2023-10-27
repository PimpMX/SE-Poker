package de.htwg.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CommunityCardsSpec extends AnyWordSpec with Matchers  {

  "CommunityCards" when {
    "created with 5 Cards (Clubs - Ace, Pip - Two, Hearts - Ten, Spades - Eight, Pip - Five)" should {

      val cards: Vector[CommunityCard] = Vector(
        new CommunityCard(Color.CLUBS, Rank.ACE, false),
        new CommunityCard(Color.PIP, Rank.TWO, false),
        new CommunityCard(Color.HEARTS, Rank.TEN, false),
        new CommunityCard(Color.SPADES, Rank.EIGHT, false),
        new CommunityCard(Color.PIP, Rank.FIVE, false)
      )

      val comCard: CommunityCards = new CommunityCards(cards)

      "return the correct cards" in {
        comCard.getCards should contain theSameElementsAs Vector(
          new CommunityCard(Color.CLUBS, Rank.ACE, false),
          new CommunityCard(Color.PIP, Rank.TWO, false),
          new CommunityCard(Color.HEARTS, Rank.TEN, false),
          new CommunityCard(Color.SPADES, Rank.EIGHT, false),
          new CommunityCard(Color.PIP, Rank.FIVE, false)
        )
      }

      "return the correct revealNext CommunityCards instance" in {
        comCard.revealNext should be(new CommunityCards(Vector(
          new CommunityCard(Color.CLUBS, Rank.ACE, true),
          new CommunityCard(Color.PIP, Rank.TWO, false),
          new CommunityCard(Color.HEARTS, Rank.TEN, false),
          new CommunityCard(Color.SPADES, Rank.EIGHT, false),
          new CommunityCard(Color.PIP, Rank.FIVE, false)
        )))
      }

      "give the correct toString with all cards unrevealed [**][**][**][**][**]" in {
        comCard.toString() should be ("[**][**][**][**][**]")
      }

      "give the correct toString with one card revealed [CA][**][**][**][**]" in {
        comCard.revealNext.toString() should be ("[CA][**][**][**][**]")
      }

      "give the correct toString with two cards revealed [CA][P2][**][**][**]" in {
        comCard.revealNext.revealNext.toString() should be ("[CA][P2][**][**][**]")
      }


    }
  }
}

package de.htwg.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class CommunityCardSpec extends AnyWordSpec with Matchers {
  "A CommunityCard" when {
    "created with Color Hearts, Rank ACE and revealed true" should {
      val card = new CommunityCard(HEARTS, ACE, true)
      "have Hearts Color" in {
        card.color should be(HEARTS)
      }
      "have rank ACE" in {
        card.rank should be(ACE)
      }

      "have revealed true" in {
        card.isRevealed should be(true)
      }

      "have a string representation of [HA]" in {
        card.toString should be("[HA]")
      }
    }
  }
}
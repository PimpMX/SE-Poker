package de.htwg.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.model.gameFieldComponent.comCardBaseImpl.CommunityCard
import de.htwg.model.gameFieldComponent.cardBaseImpl._
import de.htwg.model.gameFieldComponent.cardBaseImpl.Card
import de.htwg.model.gameFieldComponent._

class CommunityCardSpec extends AnyWordSpec with Matchers {
  "A CommunityCard" when {
    "created with Color Hearts, Rank ACE and revealed true" should {
      val card = new CommunityCard(HEARTS, ACE, true)
      "have Hearts Color" in {
        card.getColor should be(HEARTS)
      }
      "have rank ACE" in {
        card.getRank should be(ACE)
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
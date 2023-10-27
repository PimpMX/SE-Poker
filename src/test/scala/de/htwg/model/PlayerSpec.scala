package de.htwg.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers  {

  "Player" when {
    "created with player number 1, Pip - Ace and Hearts - Ten, 1000 balance and 0 betted money" should {

      val player: Player = Player(1,
        Hand((new Card(Color.PIP, Rank.ACE), new Card(Color.HEARTS, Rank.TEN))),
        1000, 0)

      "have player number 1" in {
        player.playerNum should be(1)
      }

      "have the correct hand" in {
        player.hand should be(Hand((new Card(Color.PIP, Rank.ACE), new Card(Color.HEARTS, Rank.TEN))))
      }

      "have the correct balance" in {
        player.balance should be(1000)
      }

      "have no betted money" in {
        player.moneyInPool should be(0)
      }

      "return player string 'Player1'" in {
        player.getPlayerStr should be ("Player1")
      }

      "return balance string '1000'" in {
        player.getBalanceStr should be ("1000")
      }

      "return betted string '0'" in {
        player.getBettedStr should be ("0")
      }
    }
  }
}

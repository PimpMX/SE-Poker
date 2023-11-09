package de.htwg.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameFieldSpec extends AnyWordSpec with Matchers  {

  "GameField" when {

    "created with 2 players and 5 community cards" should {

      val player1: Player = new Player(1, Hand((new Card(HEARTS, ACE), new Card(PIP, ACE))), 1000, 0)
      val player2: Player = new Player(2, Hand((new Card(HEARTS, ACE), new Card(PIP, ACE))), 1000, 0)
      val players: Vector[Player] = Vector(player1, player2)

      val cardVec: Vector[CommunityCard] = Vector(new CommunityCard(CLUBS, ACE, false),
        new CommunityCard(CLUBS, ACE, false), new CommunityCard(CLUBS, ACE, false),
        new CommunityCard(CLUBS, ACE, false), new CommunityCard(CLUBS, ACE, false))

      val comCard: CommunityCards = new CommunityCards(cardVec)

      val gamefield: GameField = GameField(players, comCard)

      "return that it has 2 players" in {
        gamefield.getNumPlayers should be(2)
      }

      "return correct the players" in {
        gamefield.getPlayers should be(players)
      }

      "return the correct community cards" in {
        gamefield.getCommunityCards should be(comCard)
      }

      "return the correct next player" in {
        gamefield.switchToNextPlayer() should be(GameField(players, comCard, 1))
      }
    }
  }
}

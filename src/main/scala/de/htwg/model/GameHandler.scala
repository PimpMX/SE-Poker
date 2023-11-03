package de.htwg.model

import de.htwg.model.GameField

object GameHandler {

  def generateTwoPlayerGame(): GameField = {

    val player1: Player = new Player(0, Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))), 1000, 0)
    val player2: Player = new Player(1, Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))), 1000, 0)
    val players: Vector[Player] = Vector(player1, player2)

    val comCard: Vector[CommunityCard] = Vector(new CommunityCard(CLUBS, ACE, false),
    new CommunityCard(CLUBS, ACE, false), new CommunityCard(CLUBS, ACE, false),
    new CommunityCard(CLUBS, ACE, false), new CommunityCard(CLUBS, ACE, false))

    val comCardO: CommunityCards = new CommunityCards(comCard)
    GameField(players, comCardO)
  }

  def generateThreePlayerGame(): GameField = {

    val player1: Player = new Player(0, Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))), 1000, 0)
    val player2: Player = new Player(1, Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))), 1000, 0)
    val player3: Player = new Player(2, Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))), 1000, 0)
    val players: Vector[Player] = Vector(player1, player2, player3)

    val comCard: Vector[CommunityCard] = Vector(new CommunityCard(CLUBS, ACE, false),
    new CommunityCard(CLUBS, ACE, false), new CommunityCard(CLUBS, ACE, false),
    new CommunityCard(CLUBS, ACE, false), new CommunityCard(CLUBS, ACE, false))

    val comCardO: CommunityCards = new CommunityCards(comCard)
    GameField(players, comCardO)
  }
}

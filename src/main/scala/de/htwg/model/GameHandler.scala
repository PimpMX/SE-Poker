package de.htwg.model

import de.htwg.model.GameField

object GameHandler {

  private var game: Option[GameField] = None

  def initGame(numPlayers: Int): GameField = {

    if(game.isEmpty) {

      val player1: Player = new Player(0,
        new Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))),
        1000, 0)

      val player2: Player = new Player(1,
        new Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))),
        1000, 0)

      val player3: Player = new Player(2,
        new Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))),
        1000, 0)

      val player4: Player = new Player(3,
        new Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))),
        1000, 0)

      val player5: Player = new Player(4,
        new Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))),
        1000, 0)
        
      val player6: Player = new Player(5,
        new Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))),
        1000, 0)

      val player7: Player = new Player(6,
        new Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))),
        1000, 0)
      
      val player8: Player = new Player(7,
        new Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))),
        1000, 0)

      val player9: Player = new Player(8,
        new Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))),
        1000, 0)

      val players: Vector[Player] = Vector(player1, player2, player3, player4, player5, player6, player7, player8, player9)

      val comCard: Vector[CommunityCard] = Vector(new CommunityCard(CLUBS, ACE, false),
      new CommunityCard(CLUBS, ACE, false), new CommunityCard(CLUBS, ACE, false),
      new CommunityCard(CLUBS, ACE, false), new CommunityCard(CLUBS, ACE, false))

      val comCardO: CommunityCards = new CommunityCards(comCard)

      game = Some(new GameField(players, comCardO))
      game.get
    } else {
      game.get
    }
  }
}

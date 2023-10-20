package de.htwg.model

import de.htwg.model.GameField

object GameHandler {

  private var game: Option[GameField] = None

  def initGame(numPlayers: Int): GameField = {

    if(game.isEmpty) {

      val player1: Player = new Player(0,
        new Hand((new Card(Color.PIP, Rank.ACE), new Card(Color.CLUBS, Rank.ACE))),
        1000, 0)

      val player2: Player = new Player(1,
        new Hand((new Card(Color.PIP, Rank.ACE), new Card(Color.CLUBS, Rank.ACE))),
        1000, 0)

      val player3: Player = new Player(2,
        new Hand((new Card(Color.PIP, Rank.ACE), new Card(Color.CLUBS, Rank.ACE))),
        1000, 0)

      val player4: Player = new Player(3,
        new Hand((new Card(Color.PIP, Rank.ACE), new Card(Color.CLUBS, Rank.ACE))),
        1000, 0)

      val players: Vector[Player] = Vector(player1, player2, player3, player4)

      game = Some(new GameField(numPlayers, players))
    }

    game.get
  }
}

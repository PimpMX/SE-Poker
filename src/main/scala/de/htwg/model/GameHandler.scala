package de.htwg.model

import de.htwg.model.GameField

object GameHandler {

  private var game: Option[GameField] = None

  def initGame(numPlayers: Int): GameField = {

    if(game.isEmpty) {

      /*

      val Player1 = new Player(0,
        (new Card(Color.PIP, Rank.ACE), new Card(Color.SPADES, Rank.TWO)), 1000, 0)

      val Player2 = new Player(1,
        (new Card(Color.PIP, Rank.ACE), new Card(Color.SPADES, Rank.TWO)), 1000, 0)

      val Player3 = new Player(2,
        (new Card(Color.PIP, Rank.ACE), new Card(Color.SPADES, Rank.TWO)), 1000, 0)

      val Player4 = new Player(3,
        (new Card(Color.PIP, Rank.ACE), new Card(Color.SPADES, Rank.TWO)), 1000, 0)
       */

      val players: Vector[Player] = Vector(new Player(0,
        new Hand((new Card(Color.PIP, Rank.ACE), new Card(Color.CLUBS, Rank.ACE))),
        1000, 0))

      game = Some(new GameField(numPlayers, players))
    }

    game.get
  }
}

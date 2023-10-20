package de.htwg.model

import de.htwg.model.GameField
import scala.util.Random
import de.htwg.model.Card

object GameHandler {

  private var game: Option[GameField] = None

  def initGame(numPlayers: Int): GameField = {

    if(game.isEmpty) {

      val players: Vector[Player] = Vector(new Player(0,
        new Hand((new Card(Color.CLUBS, Rank.ACE), new Card(Color.CLUBS, Rank.ACE))),
        1000, 0))

      game = Some(new GameField(numPlayers, players))
    }

    game.get
  }
}

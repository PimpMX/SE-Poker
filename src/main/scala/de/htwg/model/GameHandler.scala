package de.htwg.model

import de.htwg.model.GameField

object GameHandler {

  private var game: Option[GameField] = None

  def initGame(numPlayers: Int): GameField = {

    if(game.isEmpty)
      game = Some(GameField(numPlayers))

    game.get
  }
}

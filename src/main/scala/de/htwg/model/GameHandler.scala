package de.htwg.model

import de.htwg.model.GameField

object GameHandler {

  def generateGame(numPlayers: Int): Option[GameField] = {

    if(numPlayers <= 0 || numPlayers > 10) {
      Option.empty
    } else {
      val players: Vector[Player] = (0 until numPlayers).map { i =>
        new Player(i, Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))), 1000, 0)
      }.toVector
  
      val comCard: Vector[CommunityCard] = Vector.fill(5)(new CommunityCard(CLUBS, ACE, false))
      val comCardO: CommunityCards = new CommunityCards(comCard)
  
      Option(GameField(players, comCardO))
    }
  }
}

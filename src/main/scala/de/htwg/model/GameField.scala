package de.htwg.model

class GameField(numPlayers: Int, players: Vector[Player] ) {

  def getNumPlayers: Int = numPlayers;
  def getPlayers: Vector[Player] = players;
}

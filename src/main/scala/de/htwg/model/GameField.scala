package de.htwg.model

class GameField(players: Vector[Player],
                comCards: CommunityCards) {

  def getNumPlayers: Int = players.length
  def getPlayers: Vector[Player] = players
  def getCommunityCards: CommunityCards = comCards
}

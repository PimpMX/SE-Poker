package de.htwg.model

case class GameField(players: Vector[Player],
                comCards: CommunityCards,
                playerAtTurn: Int = 0) {

  def getNumPlayers: Int = players.length
  def getPlayers: Vector[Player] = players
  def getCommunityCards: CommunityCards = comCards

  def switchToNextPlayer(): GameField = {
    val nextPlayer = (playerAtTurn + 1) % players.length
    GameField(players, comCards, nextPlayer)
  }
}

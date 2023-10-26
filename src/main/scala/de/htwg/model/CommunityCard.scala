package de.htwg.model

import scala.compiletime.ops.boolean

class CommunityCard(color: Color,
                    rank: Rank,
                    revealed: Boolean) extends Card(color, rank) {
  
    def reveal: CommunityCard = new CommunityCard(color, rank, true)
    def isRevealed: Boolean = revealed
}

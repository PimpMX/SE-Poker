package de.htwg.model

class CommunityCard(color: Color,
                    rank: Rank,
                    revealed: Boolean) extends Card(color, rank) {
  
    def reveal: CommunityCard = new CommunityCard(color, rank, true)
    def isRevealed: Boolean = revealed

    override def equals(obj: Any): Boolean = obj match {
        case comCard: CommunityCard => this.color == comCard.color && this.rank == comCard.rank &&
            this.revealed == comCard.isRevealed
        case _=> false
    }
}
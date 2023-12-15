package de.htwg.model.gameFieldComponent.comCardBaseImpl

import de.htwg.model.gameFieldComponent._

class CommunityCard(color: Color, rank: Rank, revealed: Boolean) extends CommunityCardInterface {
  
    val colorToString: Map[Color, String] = Map(
        PIP -> "P",
        SPADES -> "S",
        CLUBS -> "C",
        HEARTS -> "H"
    )

    val rankToString: Map[Rank, String] = Map(
        TWO -> "2",
        THREE -> "3",
        FOUR -> "4",
        FIVE -> "5",
        SIX -> "6",
        SEVEN -> "7",
        EIGHT -> "8",
        NINE -> "9",
        TEN -> "T",
        JACK -> "J",
        QUEEN -> "Q",
        KING -> "K",
        ACE -> "A"
    )

    val getColor: Color = color
    val getRank: Rank = rank;

    def reveal: CommunityCard = new CommunityCard(color, rank, true)
    def isRevealed: Boolean = revealed

    override def equals(obj: Any): Boolean = obj match {
        case comCard: CommunityCardInterface => this.color == comCard.getColor && this.rank == comCard.getRank &&
            this.revealed == comCard.isRevealed
        case _=> false
    }

    override def hashCode(): Int = {
        val prime = 31
        var result = 1
        result = prime * result + color.hashCode
        result = prime * result + rank.hashCode
        result
    }

    override def toString: String = s"[${colorToString(color)}${rankToString(rank)}]"
}

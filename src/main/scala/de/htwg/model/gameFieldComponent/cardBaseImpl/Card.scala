package de.htwg.model.gameFieldComponent.cardBaseImpl

import de.htwg.model.gameFieldComponent._

class Card(color: Color, rank: Rank) extends CardInterface {

  val getColor: Color = color
  val getRank: Rank = rank;

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

  override def equals(obj: Any): Boolean = obj match {
    case card: CardInterface => this.color == card.getColor && this.rank == card.getRank
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

class CardFactory extends CardFactoryInterface {
  override def apply(color: Color, rank: Rank): CardInterface = new Card(color, rank)
}

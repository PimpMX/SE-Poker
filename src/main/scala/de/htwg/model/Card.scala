package de.htwg.model

sealed trait Color
case object PIP extends Color
case object SPADES extends Color
case object CLUBS extends Color
case object HEARTS extends Color

sealed trait Rank
case object TWO extends Rank
case object THREE extends Rank
case object FOUR extends Rank
case object FIVE extends Rank
case object SIX extends Rank
case object SEVEN extends Rank
case object EIGHT extends Rank
case object NINE extends Rank
case object TEN extends Rank
case object JACK extends Rank
case object QUEEN extends Rank
case object KING extends Rank
case object ACE extends Rank

class Card(argColor: Color, argRank: Rank) {

  val color: Color = argColor
  val rank: Rank = argRank;

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

  override def equals(obj: Any): Boolean =  obj match {
    case card: Card => this.color == card.color && this.rank == card.rank
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

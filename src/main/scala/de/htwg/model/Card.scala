package de.htwg.model

enum Color:
  case PIP, SPADES, CLUBS, HEARTS

enum Rank:
  case TWO, THREE, FOUR, FIVE,
  SIX, SEVEN, EIGHT, NINE, TEN,
  JACK, QUEEN, KING, ACE

class Card(argColor: Color, argRank: Rank) {

  val color: Color = argColor
  val rank: Rank = argRank;

  def getColor: Color = color
  def getRank: Rank = rank

  /*

  val colorToString: Map[Color.Value, String] = Map(
    Color.CLUBS -> "C",
    Color.PIP -> "P",
    Color.SPADES -> "S",
    Color.HEARTS -> "H"
  )

  val rankToString: Map[Rank.Value, String] = Map(
    Rank.TWO -> "2",
    Rank.THREE -> "3",
    Rank.FOUR -> "4",
    Rank.FIVE -> "5",
    Rank.SIX -> "6",
    Rank.SEVEN -> "7",
    Rank.EIGHT -> "8",
    Rank.NINE -> "9",
    Rank.TEN -> "T",
    Rank.JACK -> "J",
    Rank.QUEEN -> "Q",
    Rank.KING -> "K",
    Rank.ACE -> "A"
  )

  override def toString: String = s"[${colorToString(color)}${rankToString(rank)}]"
  */
}

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
  
}

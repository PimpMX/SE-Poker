package de.htwg.model.gameFieldComponent

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

trait CardInterface {
  def getRank: Rank
  def getColor: Color
  def toString: String
  def equals(obj: Any): Boolean
  def hashCode: Int
}
package de.htwg.model

case class Hand(cards: (Card, Card)) {

  override def toString: String = s"${cards._1}${cards._2}"
}

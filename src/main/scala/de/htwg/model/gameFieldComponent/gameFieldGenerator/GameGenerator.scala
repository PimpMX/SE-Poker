package de.htwg.model.gameFieldComponent.gameFieldGenerator

import de.htwg.model.gameFieldComponent._
import de.htwg.model._
import de.htwg.model.gameFieldComponent.gameFieldBaseImpl._
import de.htwg.model.gameFieldComponent.cardBaseImpl._
import de.htwg.model.gameFieldComponent.comCardsBaseImpl._
import de.htwg.model.gameFieldComponent.comCardBaseImpl._
import de.htwg.model.gameFieldComponent.playerBaseImpl._

object GameGenerator {

  def apply(numPlayers: Int): Option[GameField] = {

    if(numPlayers <= 0 || numPlayers > 10) {
      Option.empty
    } else {

      val playerBuilder = new PlayerBuilder()

      val players: Vector[Player] = (0 until numPlayers).map { i =>
        playerBuilder.setPlayerNum(i)
          .setHand(Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))))
          .setBalance(1000)
          .setMoneyInPool(0)
          .build()
      }.toVector
  
      val comCard: Vector[CommunityCard] = Vector.fill(5)(new CommunityCard(CLUBS, ACE, false))
      val comCardO: CommunityCards = new CommunityCards(comCard)
  
      Option(GameField(players, comCardO))
    }
  }
}

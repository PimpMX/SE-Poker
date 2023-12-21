package de.htwg.model.gameFieldComponent.gameFieldGenerator

import de.htwg.model.gameFieldComponent._
import de.htwg.model._
import de.htwg.model.gameFieldComponent.gameFieldBaseImpl._
import de.htwg.model.gameFieldComponent.cardBaseImpl._
import de.htwg.model.gameFieldComponent.comCardsBaseImpl._
import de.htwg.model.gameFieldComponent.comCardBaseImpl._
import de.htwg.model.gameFieldComponent.playerBaseImpl._
import com.google.inject.Guice
import de.htwg.TexasHoldEmModule

class GameGenerator extends GameGeneratorInterface {

  val injector = Guice.createInjector(new TexasHoldEmModule)

  val playerBuilder = injector.getInstance(classOf[PlayerBuilderInterface])
  val gameFieldFactory = injector.getInstance(classOf[GameFieldFactoryInterface])
  val communityCardFactory = injector.getInstance(classOf[CommunityCardFactoryInterface])
  val communityCardsFactory = injector.getInstance(classOf[CommunityCardsFactoryInterface])
  
  def apply(numPlayers: Int): Option[GameFieldInterface] = {

    if(numPlayers <= 0 || numPlayers > 10) {
      Option.empty
    } else {

      val players: Vector[PlayerInterface] = (0 until numPlayers).map { i =>
        playerBuilder.setPlayerNum(i)
          .setHand(Hand((new Card(PIP, ACE), new Card(CLUBS, ACE))))
          .setBalance(1000)
          .setMoneyInPool(0)
          .build()
      }.toVector
  
      val comCard: Vector[CommunityCardInterface] = Vector.fill(5)(communityCardFactory(PIP, ACE, false))
      val comCardO: CommunityCardsInterface = communityCardsFactory(comCard)
  
      Option(gameFieldFactory(players, comCardO, 0))
    }
  }
}

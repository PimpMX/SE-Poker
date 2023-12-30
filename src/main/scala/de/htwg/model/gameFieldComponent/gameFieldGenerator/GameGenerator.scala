package de.htwg.model.gameFieldComponent.gameFieldGenerator

import de.htwg.model.gameFieldComponent._
import de.htwg.model._
import com.google.inject.Guice
import de.htwg.TexasHoldEmModule

class GameGenerator extends GameGeneratorInterface {

  val injector = Guice.createInjector(new TexasHoldEmModule)

  val playerBuilder = injector.getInstance(classOf[PlayerBuilderInterface])
  val gameFieldFactory = injector.getInstance(classOf[GameFieldFactoryInterface])
  val cardSetFactory = injector.getInstance(classOf[CardSetFactoryInterface])
  val communityCardsFactory = injector.getInstance(classOf[CommunityCardsFactoryInterface])
  
  def apply(numPlayers: Int): Option[GameFieldInterface] = {

    if(numPlayers <= 0 || numPlayers > 10) {
      Option.empty
    } else {

      // Generate new card set and shuffle it
      var cardSet = cardSetFactory().initialize.shuffle 

      // Generate every player entity
      val players: Vector[PlayerInterface] = (0 until numPlayers).map { i =>
        
        // Take 2 cards from card set
        val (hand, newCardSet) = cardSet.takeCard(2)
        
        // Update card set
        cardSet = newCardSet 
        
        playerBuilder.setPlayerNum(i)
        .setHand(Hand(hand(0), hand(1)))
        .setBalance(1000)
        .setMoneyInPool(0)
        .build()
      
      }.toVector

      // At last take 5 community cards
  
      val communityCards: CommunityCardsInterface =
        communityCardsFactory(cardSet.takeCommunityCard(5)._1)
  
      Option(gameFieldFactory(players, communityCards, 0))
    }
  }
}

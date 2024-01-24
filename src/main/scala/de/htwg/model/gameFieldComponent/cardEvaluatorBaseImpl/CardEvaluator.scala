package de.htwg.model.gameFieldComponent.cardEvaluatorBaseImpl

import de.htwg.model.gameFieldComponent._
import com.google.inject.Guice
import de.htwg.TexasHoldEmModule

class CardEvaluator extends CardEvaluatorInterface {

  val injector = Guice.createInjector(new TexasHoldEmModule)
  val cardFactory = injector.getInstance(classOf[CardFactoryInterface])

  //  The method rankCards return a sorted array of CardEvaluations
  //  descending in order of ranking. This means the first evaluation
  //  is the highest card among the players 

  override def rankCards(players: Vector[PlayerInterface],
    comCards: CommunityCardsInterface): Vector[CardEvaluation] = {

    val cardEvaluations: Vector[CardEvaluation] = players.map(player => {
      // print(determineHighestRank(player.getHand, comCards.getCards).toString() + "\n")
      CardEvaluation(player, determineHighestRank(player.getHand, comCards.getCards))
    })

    cardEvaluations
  }

  def determineHighestRank(hand: Hand, comCards: Vector[CardInterface]): HandRank = {
    
    val playerCards: Vector[CardInterface] = Vector(
      hand.cards._1,
      hand.cards._2,
    ).concat(comCards)

    val pairs: Option[HandRank] = checkForPairs(playerCards)

    if(pairs.isDefined)
      pairs.get
    else ROYAL_FLUSH
  }

  //  Checks if there are any pairs (Two/Three/Four and returns the highest of it)

  def checkForPairs(cards: Vector[CardInterface]): Option[HandRank] = {
    
    val ranks: Vector[Rank] = cards.map(card => card.getRank)

    val rankCounts: Map[Rank, Int] = ranks.groupBy(identity)
                                          .view.mapValues(_.size)
                                          .toMap

    printf(rankCounts.toString())

    None
  }

  def checkStraight(cards: Vector[CardInterface]): Boolean = {
    false
  }

  def checkFlush(cards: Vector[CardInterface]): Boolean = {
    false
  }

  def checkStraightFlush(cards: Vector[CardInterface]): Boolean = {
    false
  }

  def checkRoyalFlush(cards: Vector[CardInterface]): Boolean = {
    false
  }
}

class CardEvaluatorFactory extends CardEvaluatorFactoryInterface {
  def apply(): CardEvaluatorInterface = new CardEvaluator();
}

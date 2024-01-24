package de.htwg.model.gameFieldComponent.cardEvaluatorBaseImpl

import de.htwg.model.gameFieldComponent._
import com.google.inject.Guice
import de.htwg.TexasHoldEmModule

class CardEvaluator extends CardEvaluatorInterface {

  //  The method rankCards return a sorted array of CardEvaluations
  //  descending in order of ranking. This means the first evaluation
  //  is the highest card among the players 

  override def rankCards(players: Vector[PlayerInterface],
    comCards: CommunityCardsInterface): Vector[CardEvaluation] = {

    //  We determine the highest possible handRank for each player 

    val cardEvaluations: Vector[CardEvaluation] = players.map(player => {
      val (highestRank, handRank) = determineHighestRank(player.getHand, comCards.getCards)
      CardEvaluation(player, handRank, highestRank)
    })

    //  Then we sort it so that HandRank is the first decider of position
    //  then the highest card (when two players have the same highest card)
    //  and at last we compare them by their second card in their hand 
    //  (in case the previous two criteria couldn't decide who's hand is better)

    val sortedCardEvaluations: Vector[CardEvaluation] = cardEvaluations.sortBy { cardEvaluation =>
      (
        -handRankToInt(cardEvaluation.handRank),
        -rankToInt(cardEvaluation.highestCard),
        -findSecondHighestCard(cardEvaluation.player.getHand, cardEvaluation.highestCard)
      )
    }

    sortedCardEvaluations
  }

  def findSecondHighestCard(hand: Hand, highestRank: Rank): Int = {
    if(highestRank == hand.cards._1.getRank) {
      rankToInt(hand.cards._2.getRank)
    } else {
      rankToInt(hand.cards._1.getRank)
    }
  }

  //  This method returns the highest HandRank a player has 

  def determineHighestRank(hand: Hand, comCards: Vector[CardInterface]): (Rank, HandRank) = {
    
    val playerCards: Vector[CardInterface] = Vector(
      hand.cards._1,
      hand.cards._2,
    ).concat(comCards)

    //  What we do is: check the card combinations from strongest to weakest

    val isRoyalFlush = checkRoyalFlush(playerCards)
    
    if(isRoyalFlush._2)
      return (isRoyalFlush._1.get, ROYAL_FLUSH)

    val isStraightFlush = checkStraightFlush(playerCards)

    if(isStraightFlush._2)
      return (isStraightFlush._1.get, STRAIGHT_FLUSH)

    val pairs = searchForPairs(playerCards)

    if(pairs.headOption.isDefined && pairs.headOption.get._2 == FOUR_OF_A_KIND)
      return (pairs.headOption.get._1, FOUR_OF_A_KIND)

    val fullHouse = searchForFullHouse(pairs)

    if(fullHouse.headOption.isDefined && fullHouse.headOption.get._2 == FULL_HOUSE)
      return (fullHouse.headOption.get._1, FULL_HOUSE)

    val isFlush = checkFlush(playerCards)

    if(isFlush._2)
      return (isFlush._1.get, FLUSH)

    val isStraight = checkStraight(playerCards)

    if(isStraight._2)
      return (isStraight._1.get, STRAIGHT)

    if(pairs.headOption.isDefined && pairs.headOption.get._2 == THREE_OF_A_KIND)
      return (pairs.headOption.get._1, THREE_OF_A_KIND)

    val twoPairs = searchForTwoPairs(pairs)

    if(twoPairs.headOption.isDefined && twoPairs.headOption.get._2 == TWO_PAIR)
      return (pairs.headOption.get._1, TWO_PAIR)

    if(pairs.headOption.isDefined && pairs.headOption.get._2 == ONE_PAIR)
      return (pairs.headOption.get._1, ONE_PAIR)

    //  If we arrive here that means that there hasn't been any card combination
    //  so what we do is find the best high card

    val sortedHighCards = pairs.sortBy {
      case (rank, _) => rankToInt(rank)
    } (Ordering[Int]).reverse

    (sortedHighCards(0)._1, sortedHighCards(0)._2)
  }

  /*
   *  Finds all pairs in the cards of a player and returns them,
   *  if the results are processed further, one can evaluate the following 
   *  HandRanks from this:
   *   
   *  - One-Pair
   *  - Two-Pair (consists of two One-Pairs)
   *  - Three-Of-A-Kind
   *  - Four-Of-A-Kind
   *  - Full-House (consists of a Three-Of-A-Kind and Two-Pair)
   */

  def searchForPairs(cards: Vector[CardInterface]): Vector[(Rank, HandRank)] = {
    
    //  Counts the occurence of the ranks in the cards (player hand + community cards)

    val rankCounts: Map[Rank, Int] = cards.map(card => card.getRank)
        .groupBy(identity)
        .view.mapValues(_.size)
        .toMap

    //  Sort it by occurence

    val sortedCounted: Vector[(Rank, Int)] = rankCounts.toVector.sortBy(-_._2)

    //  Then evaluate the occurence to HandRank

    val evaluated: Vector[Option[(Rank, HandRank)]] = sortedCounted.map((rank, occurence) => { 
      occurence match {
        case 1 => Some((rank, HIGH_CARD))
        case 2 => Some((rank, ONE_PAIR))
        case 3 => Some((rank, THREE_OF_A_KIND))
        case 4 => Some((rank, FOUR_OF_A_KIND))
        case _ => None
      }
    })

    //  Collect the Option's that contain a value (They should all contain a value
    //  since there's no way a card occurs more than 4 Times in a CardSet)

    evaluated.collect {
      case Some(value) => value
    }
  }

  def searchForFullHouse(handData: Vector[(Rank, HandRank)]): Vector[(Rank, HandRank)] = {

    val three_of_a_kind = handData.find((rank, handRank) => handRank == THREE_OF_A_KIND)
    val one_pair = handData.find((rank, handRank) => handRank == ONE_PAIR)

    //  If we can find a three pair and a two pair we have a full house!

    if(three_of_a_kind.isDefined && one_pair.isDefined) {
      
      //  Then we filter out those two in preparation to add the FULL_HOUSE
      //  HandRank in there

      val filtered = handData.filterNot {
        case (_, handRank) => handRank == THREE_OF_A_KIND || handRank == ONE_PAIR 
      }

      val three_oak_rank = rankToInt(three_of_a_kind.get._1)
      val one_pair_rank = rankToInt(one_pair.get._1)

      //  The full house has the card rank of the highest card
      //  This is assigned in case two people have a full house so you can decide
      //  who has the higher full house (Rules same hand rank => highest card in hand wins)

      var fullHouseRank: Rank = if(three_oak_rank > one_pair_rank) three_of_a_kind.get._1
        else if(three_oak_rank < one_pair_rank) one_pair.get._1
        else three_of_a_kind.get._1

      val nHandData = filtered :+ (fullHouseRank, FULL_HOUSE)

      nHandData.sortBy {
        case (_, handRank) => handRankToInt(handRank) 
      } (Ordering[Int].reverse)
    } else {
      handData
    }
  }

  def searchForTwoPairs(handData: Vector[(Rank, HandRank)]) = {

    //  Check if there are two occurences of a one pair

    val twoPairs = handData.filter { case (_, handRank) => handRank == ONE_PAIR }

    if(twoPairs.length >= 2) {

      //  Filter out the two one pair to replace them with the two pair in a moment

      val filtered = handData.filter { case (_, handRank) => handRank != ONE_PAIR}

      //  Sort the two pair by HandRank

      val ordered = twoPairs.sortBy {
        case (rank, _) => rankToInt(rank)
      }

      //  Add the two pair

      val nHandData = filtered :+ (ordered(0)._1, TWO_PAIR)

      nHandData.sortBy {
        case (_, handRank) => handRankToInt(handRank) 
      } (Ordering[Int].reverse)

    } else {
      handData
    }
  }

  def checkStraight(cards: Vector[CardInterface]): (Option[Rank], Boolean) = {

    //  Get the distinct ranks of our cards

    val distinctRanks = cards.map(card => card.getRank).distinct.sortBy {
      case rank => rankToInt(rank)
    }

    val rankNumbers = distinctRanks.map(rank => { rankToInt(rank) })
    
    //  Check if there is a sequence of 5 elements in order

    val matchingSequence: Option[Vector[Int]] = rankNumbers.sliding(5).collectFirst {
      case subarray if subarray.sorted == subarray && subarray.max - subarray.min == 4 =>
        subarray.toVector
    }

    if(matchingSequence.isDefined) {
      val maxRank = matchingSequence.get.maxOption.get
      (intToRank(maxRank), true)
    } else {
      (None, false)
    }
  }

  def checkFlush(cards: Vector[CardInterface]): (Option[Rank], Boolean) = {
    
    //  Count the colors

    val colorCounts: Map[Color, Int] = cards.map((card) => card.getColor)
      .groupBy(identity)
      .view.mapValues(_.size)
      .toMap

    //  Sort them descendingly

    val sortedCounted: Vector[(Color, Int)] = colorCounts.toVector.sortBy(-_._2)

    //  If we have five colors we have a flush

    if(sortedCounted(0)._2 >= 5) {

      //  Now we have to find out which the highest card is
      //  which defines the strength of our flush

      val flushColor = sortedCounted(0)._1
      val flushCards = cards.filter(card => card.getColor == flushColor)

      val sortedByRank = flushCards.sortBy {
        case card => rankToInt(card.getRank)
      } (Ordering[Int].reverse)

      (Some(sortedByRank(0).getRank), true)
    } else {
      (None, false)
    }
  }

  def checkStraightFlush(cards: Vector[CardInterface]): (Option[Rank], Boolean) = {

    //  Count the colors

    val colorCounts: Map[Color, Int] = cards.map((card) => card.getColor)
      .groupBy(identity)
      .view.mapValues(_.size)
      .toMap

    //  Sort them descendingly

    val sortedCounted: Vector[(Color, Int)] = colorCounts.toVector.sortBy(-_._2)

    //  If we have five colors we have a flush

    if(sortedCounted(0)._2 >= 5) {

      val flushColor = sortedCounted(0)._1
      val flushCards = cards.filter(card => card.getColor == flushColor)

      //  Now we have to check if the cards involved in the flush

      if(checkStraight(flushCards)._2) {

        // Get the highest Rank of the Straight Flush to determine
        // the "strength" of this straight flush

        val sortedByRank = flushCards.sortBy {
          case card => rankToInt(card.getRank)
        } (Ordering[Int].reverse)
  
        (Some(sortedByRank(0).getRank), true)
      
      } else {
        (None, false)
      }
    } else {
      (None, false)
    }
  }

  def checkRoyalFlush(cards: Vector[CardInterface]): (Option[Rank], Boolean) = {
    
    //  Count the colors

    val colorCounts: Map[Color, Int] = cards.map((card) => card.getColor)
      .groupBy(identity)
      .view.mapValues(_.size)
      .toMap

    //  Sort them descendingly

    val sortedCounted: Vector[(Color, Int)] = colorCounts.toVector.sortBy(-_._2)

    //  If we have five colors we have a flush

    if(sortedCounted(0)._2 >= 5) {

      //  Now we have to find out which the highest card is
      //  which defines the strength of our flush

      val flushColor = sortedCounted(0)._1
      val flushCards = cards.filter(card => card.getColor == flushColor)

      //  At last check if all the cards required for a royal flush are there

      if(flushCards.find(card => card.getRank == ACE).isDefined &&
        flushCards.find(card => card.getRank == KING).isDefined &&
        flushCards.find(card => card.getRank == QUEEN).isDefined &&
        flushCards.find(card => card.getRank == JACK).isDefined &&
        flushCards.find(card => card.getRank == TEN).isDefined) {
        
        (Some(ACE), true)
      } else {
        (None, false)
      }

    } else {
      (None, false)
    }
  }

  //  A little sloppy but we don't have much time anymore
  //  used to evaluating which rank is higher

  def rankToInt(rank: Rank): Int = {

    rank match {
      case TWO => 0
      case THREE => 1  
      case FOUR => 2 
      case FIVE => 3 
      case SIX => 4 
      case SEVEN => 5  
      case EIGHT => 6
      case NINE => 7 
      case TEN => 8
      case JACK => 9 
      case QUEEN => 10
      case KING => 11 
      case ACE => 12 
    }
  }

  //  This also is complete bullshit

  def intToRank(rankAsInt: Int): Option[Rank] = {

    rankAsInt match {
      case 0 => Some(TWO)
      case 1 => Some(THREE)
      case 2 => Some(FOUR)
      case 3 => Some(FIVE)
      case 4 => Some(SIX)
      case 5 => Some(SEVEN)
      case 6 => Some(EIGHT)
      case 7 => Some(NINE)
      case 8 => Some(TEN)
      case 9 => Some(JACK)
      case 10 => Some(QUEEN)
      case 11 => Some(KING)
      case 12 => Some(ACE)
      case _ => None
    }
  }

  //  Also ugly, there are better ways

  def handRankToInt(handRank: HandRank): Int = {

    handRank match {
      case ROYAL_FLUSH => 9
      case STRAIGHT_FLUSH => 8
      case FOUR_OF_A_KIND => 7
      case FULL_HOUSE => 6
      case FLUSH => 5
      case STRAIGHT => 4
      case THREE_OF_A_KIND => 3
      case TWO_PAIR => 2
      case ONE_PAIR => 1
      case HIGH_CARD => 0
    }
  }
}

class CardEvaluatorFactory extends CardEvaluatorFactoryInterface {
  def apply(): CardEvaluatorInterface = new CardEvaluator();
}

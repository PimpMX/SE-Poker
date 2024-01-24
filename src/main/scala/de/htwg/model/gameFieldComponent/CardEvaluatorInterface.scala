package de.htwg.model.gameFieldComponent

sealed trait HandRank
case object ROYAL_FLUSH extends HandRank
case object STRAIGHT_FLUSH extends HandRank
case object FOUR_OF_A_KIND extends HandRank
case object FULL_HOUSE extends HandRank
case object FLUSH extends HandRank
case object STRAIGHT extends HandRank
case object THREE_OF_A_KIND extends HandRank
case object TWO_PAIR extends HandRank
case object ONE_PAIR extends HandRank
case object HIGH_CARD extends HandRank

case class CardEvaluation(player: PlayerInterface, handRank: HandRank, highestCard: Rank) {}

trait CardEvaluatorInterface {

    //  The method rankCards return a sorted array of CardEvaluations
    //  descending in order of ranking. This means the first evaluation
    //  is the highest card among the players 

    def rankCards(players: Vector[PlayerInterface], comCards: CommunityCardsInterface): Vector[CardEvaluation]
}

trait CardEvaluatorFactoryInterface {
    def apply(): CardEvaluatorInterface
}

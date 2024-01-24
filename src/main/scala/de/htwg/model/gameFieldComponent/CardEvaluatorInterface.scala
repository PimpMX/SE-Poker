package de.htwg.model.gameFieldComponent

sealed trait HandRank
case object ROYAL_FLUSH extends HandRank
case object STRAIGHT_FLUSH extends HandRank
case object FOUR_PAIR extends HandRank
case object FLUSH extends HandRank 
case object STRAIGHT extends HandRank
case object THREE_PAIR extends HandRank
case object TWO_PAIR extends HandRank 
case object PAIR extends HandRank 
case object HIGH_CARD extends HandRank

case class CardEvaluation(player: PlayerInterface, rank: HandRank) {}

trait CardEvaluatorInterface {
    def rankCards(players: Vector[PlayerInterface], comCards: CommunityCardsInterface): Vector[CardEvaluation]
}

trait CardEvaluatorFactoryInterface {
    def apply(): CardEvaluatorInterface
}

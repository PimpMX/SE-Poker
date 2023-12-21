package de.htwg.model.gameFieldComponent.comCardsBaseImpl

import de.htwg.model.gameFieldComponent.CommunityCardInterface
import de.htwg.model.gameFieldComponent.CommunityCardsInterface
import de.htwg.model.gameFieldComponent.CommunityCardsFactoryInterface

class CommunityCards(cards: Vector[CommunityCardInterface]) extends CommunityCardsInterface {
  
    def getCards: Vector[CommunityCardInterface] = cards

    def revealNext: CommunityCardsInterface = {

        var cardHasBeenRevealed = false
        
        val newCards: Vector[CommunityCardInterface] = cards.map(card => {
            if (!cardHasBeenRevealed && !card.isRevealed) {
                cardHasBeenRevealed = true
                card.reveal
            } else {
                card
            }
        })

        new CommunityCards(newCards)
    }

    override def toString(): String = {
        cards.map(card => { if(card.isRevealed) card.toString else "[**]"}).mkString("")
    }

    override def equals(obj: Any): Boolean = obj match {
        case comCard: CommunityCards => this.cards sameElements comCard.getCards
        case _=> false
    }
}

class CommunityCardsFactory extends CommunityCardsFactoryInterface {
    override def apply(cards: Vector[CommunityCardInterface]): CommunityCardsInterface = new CommunityCards(cards)
}

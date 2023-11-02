package de.htwg.model

class CommunityCards(cards: Vector[CommunityCard]) {
  
    def getCards: Vector[CommunityCard] = cards

    def revealNext: CommunityCards = {

        var cardHasBeenRevealed = false
        
        val newCards: Vector[CommunityCard] = cards.map(card => {
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

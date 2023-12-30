package de.htwg.model.gameFieldComponent.cardSetBaseImpl

import de.htwg.model.gameFieldComponent._
import com.google.inject.Guice
import de.htwg.TexasHoldEmModule
import scala.util.Random

class CardSet(var freeCards: Vector[CardInterface], var takenCards: Vector[CardInterface]) extends CardSetInterface {

    val injector = Guice.createInjector(new TexasHoldEmModule)
    val cardFactory = injector.getInstance(classOf[CardFactoryInterface])

    override def initialize: CardSetInterface = {

        //  Generate all 52 cards

        val regularCardSet = (for {
            color <- List(PIP, SPADES, CLUBS, HEARTS)
            rank <- List(TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE)
        } yield cardFactory(color, rank)).toVector

        new CardSet(regularCardSet, Vector())
    }

    override def shuffle: CardSetInterface = {

        Random.setSeed(System.currentTimeMillis)        

        val shuffledFreeCards = Random.shuffle(freeCards)
        new CardSet(shuffledFreeCards, takenCards)
    }

    override def getAvailableCards: Vector[CardInterface] = {
        freeCards
    }

    override def getTakenCards: Vector[CardInterface] = {
        takenCards
    }

    override def take(amount: Int): (Vector[CardInterface], CardSetInterface) = {
        val (taken, free) = freeCards.splitAt(amount)
        (taken, new CardSet(free, takenCards ++ taken))
    }
}

class CardSetFactory extends CardSetFactoryInterface {
    def apply(): CardSetInterface = new CardSet(Vector(), Vector())
}

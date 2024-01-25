package de.htwg.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import de.htwg.model.gameFieldComponent.gameFieldBaseImpl.GameField
import de.htwg.model.gameFieldComponent.gameFieldGenerator.GameGenerator
import de.htwg.model.gameFieldComponent._
import de.htwg.model.gameFieldComponent.playerBaseImpl.Player
import de.htwg.model.gameFieldComponent.cardBaseImpl.Card
import de.htwg.model.gameFieldComponent.comCardBaseImpl.CommunityCard
import de.htwg.model.gameFieldComponent.cardEvaluatorBaseImpl.CardEvaluator
import de.htwg.model.gameFieldComponent.gameFieldBaseImpl.CLIViewStrategy



class GameFieldSpec extends AnyWordSpec with Matchers  {

    "GameField" should {

        val gameGenerator = new GameGenerator()

        "return correct amount of players" in {
            val gameField = gameGenerator(1)
            gameField.isDefined shouldBe(true)
            gameField.get.getNumPlayers shouldBe(1)
        }

        "return correct amount of community cards" in {
            val gameField = gameGenerator(1)
            gameField.isDefined shouldBe(true)
            gameField.get.getCommunityCards.getCards.length shouldBe(5)
        }

        "have a working switch player method" in {
            val gameField = gameGenerator(2)
            gameField.isDefined shouldBe(true)
            gameField.get.switchToNextPlayer.getPlayerAtTurn.getPlayerNum shouldBe(1)
        }

        "return correct player at turn" in {
            val gameField = gameGenerator(1)
            gameField.isDefined shouldBe(true)
            gameField.get.getPlayerAtTurn.getPlayerNum shouldBe(0)
        }

        "have a working bet method" in {
            val gameField = gameGenerator(1)
            gameField.isDefined shouldBe(true)

            gameField.get.activePlayerBet(25).get.getPlayerAtTurn.getBalance shouldBe(975)
            gameField.get.activePlayerBet(10000) shouldBe(Option.empty)
        }

        "have a working all-in method" in {
            val gameField = gameGenerator(2)
            gameField.isDefined shouldBe(true)
            gameField.get.activePlayerBet(25)
            gameField.get.activePlayerAllIn.isDefined shouldBe(true)
            // gameField.get.activePlayerAllIn().get.activePlayerAllIn() shouldBe(Option.empty)
        }

        "have a working check method" in {
            val gameField = gameGenerator(1)
            gameField.isDefined shouldBe(true)
            gameField.get.activePlayerCheck.get.getPlayerAtTurn.getPlayerNum shouldBe(0)
        }

        "have a working fold method" in {
            val gameField = gameGenerator(1)
            gameField.isDefined shouldBe(true)
            gameField.get.activePlayerFold.isDefined shouldBe(true)
        }

        "have a working toString method" in {
            val gameField = gameGenerator(1)
            gameField.isDefined shouldBe(true)
            gameField.get.toString() shouldBe(gameField.get.toString())
        }
    }
}


class CardEvaluatorSpec extends AnyWordSpec with Matchers {

    "The CardEvaluator" should {
        "correctly rank cards with Full House" in {
            val player1 = new Player(1,
                Hand((new Card(DIAMONDS, ACE), new Card(HEARTS, ACE))),
                1000, 0)
            val players = Vector[PlayerInterface](player1)
            
            val Cards: Vector[CommunityCard] = Vector(
                new CommunityCard(CLUBS, ACE, false),
                new CommunityCard(DIAMONDS, TWO, false),
                new CommunityCard(HEARTS, TEN, false),
                new CommunityCard(SPADES, TEN, false),
                new CommunityCard(DIAMONDS, FIVE, false)
            )

            val comCards = new CommunityCardsInterface {
                override def getCards: Vector[CommunityCardInterface] = Cards
                override def revealNext: CommunityCardsInterface = this
                override def toString(): String = ""
                override def equals(obj: Any): Boolean = false}

            val rankedCards = CardEvaluator.apply().rankCards(players, comCards)
            rankedCards(0).highestCard should be(ACE)
            rankedCards(0).handRank should be(FULL_HOUSE)
            rankedCards(0).player should be(player1)
        }

        "correctly rank cards with Four of a Kind" in {
            val player1 = new Player(1,
                Hand((new Card(DIAMONDS, ACE), new Card(HEARTS, ACE))),
                1000, 0)
            val players = Vector[PlayerInterface](player1)
            
            val Cards: Vector[CommunityCard] = Vector(
                new CommunityCard(CLUBS, ACE, false),
                new CommunityCard(DIAMONDS, ACE, false),
                new CommunityCard(HEARTS, TEN, false),
                new CommunityCard(SPADES, TEN, false),
                new CommunityCard(DIAMONDS, FIVE, false)
            )

            val comCards = new CommunityCardsInterface {
                override def getCards: Vector[CommunityCardInterface] = Cards
                override def revealNext: CommunityCardsInterface = this
                override def toString(): String = ""
                override def equals(obj: Any): Boolean = false}

            val rankedCards = CardEvaluator.apply().rankCards(players, comCards)
            rankedCards(0).highestCard should be(ACE)
            rankedCards(0).handRank should be(FOUR_OF_A_KIND)
            rankedCards(0).player should be(player1)
        }

        "correctly rank cards with Royal Flush" in {
            val player1 = new Player(1,
                Hand((new Card(DIAMONDS, ACE), new Card(HEARTS, ACE))),
                1000, 0)
            val players = Vector[PlayerInterface](player1)
            
            val Cards: Vector[CommunityCard] = Vector(
                new CommunityCard(CLUBS, ACE, false),
                new CommunityCard(DIAMONDS, KING, false),
                new CommunityCard(DIAMONDS, QUEEN, false),
                new CommunityCard(DIAMONDS, JACK, false),
                new CommunityCard(DIAMONDS, TEN, false)
            )

            val comCards = new CommunityCardsInterface {
                override def getCards: Vector[CommunityCardInterface] = Cards
                override def revealNext: CommunityCardsInterface = this
                override def toString(): String = ""
                override def equals(obj: Any): Boolean = false}

            val rankedCards = CardEvaluator.apply().rankCards(players, comCards)
            rankedCards(0).highestCard should be(ACE)
            rankedCards(0).handRank should be(ROYAL_FLUSH)
            rankedCards(0).player should be(player1)
        }

        "correctly rank cards with Three of a Kind" in {
            val player1 = new Player(1,
                Hand((new Card(DIAMONDS, ACE), new Card(HEARTS, ACE))),
                1000, 0)
            val players = Vector[PlayerInterface](player1)
            
            val Cards: Vector[CommunityCard] = Vector(
                new CommunityCard(CLUBS, ACE, false),
                new CommunityCard(DIAMONDS, TWO, false),
                new CommunityCard(HEARTS, FOUR, false),
                new CommunityCard(SPADES, TEN, false),
                new CommunityCard(DIAMONDS, FIVE, false)
            )

            val comCards = new CommunityCardsInterface {
                override def getCards: Vector[CommunityCardInterface] = Cards
                override def revealNext: CommunityCardsInterface = this
                override def toString(): String = ""
                override def equals(obj: Any): Boolean = false}

            val rankedCards = CardEvaluator.apply().rankCards(players, comCards)
            rankedCards(0).highestCard should be(ACE)
            rankedCards(0).handRank should be(THREE_OF_A_KIND)
            rankedCards(0).player should be(player1)
        }

        "correctly rank cards with straight" in {
            val player1 = new Player(1,
                Hand((new Card(DIAMONDS, SIX), new Card(HEARTS, ACE))),
                1000, 0)
            val players = Vector[PlayerInterface](player1)
            
            val Cards: Vector[CommunityCard] = Vector(
                new CommunityCard(CLUBS, ACE, false),
                new CommunityCard(DIAMONDS, TWO, false),
                new CommunityCard(HEARTS, THREE, false),
                new CommunityCard(SPADES, FOUR, false),
                new CommunityCard(DIAMONDS, FIVE, false)
            )

            val comCards = new CommunityCardsInterface {
                override def getCards: Vector[CommunityCardInterface] = Cards
                override def revealNext: CommunityCardsInterface = this
                override def toString(): String = ""
                override def equals(obj: Any): Boolean = false}

            val rankedCards = CardEvaluator.apply().rankCards(players, comCards)
            rankedCards(0).highestCard should be(SIX)
            rankedCards(0).handRank should be(STRAIGHT)
            rankedCards(0).player should be(player1)
        }

        "correctly rank cards with flush" in {
            val player1 = new Player(1,
                Hand((new Card(DIAMONDS, SIX), new Card(HEARTS, ACE))),
                1000, 0)
            val players = Vector[PlayerInterface](player1)
            
            val Cards: Vector[CommunityCard] = Vector(
                new CommunityCard(CLUBS, ACE, false),
                new CommunityCard(CLUBS, TWO, false),
                new CommunityCard(CLUBS, THREE, false),
                new CommunityCard(CLUBS, FOUR, false),
                new CommunityCard(CLUBS, FIVE, false)
            )

            val comCards = new CommunityCardsInterface {
                override def getCards: Vector[CommunityCardInterface] = Cards
                override def revealNext: CommunityCardsInterface = this
                override def toString(): String = ""
                override def equals(obj: Any): Boolean = false}

            val rankedCards = CardEvaluator.apply().rankCards(players, comCards)
            rankedCards(0).highestCard should be(ACE)
            rankedCards(0).handRank should be(FLUSH)
            rankedCards(0).player should be(player1)
        }

        "correctly rank cards with straight flush" in {
            val player1 = new Player(1,
                Hand((new Card(CLUBS, SIX), new Card(HEARTS, ACE))),
                1000, 0)
            val players = Vector[PlayerInterface](player1)
            
            val Cards: Vector[CommunityCard] = Vector(
                new CommunityCard(CLUBS, ACE, false),
                new CommunityCard(CLUBS, TWO, false),
                new CommunityCard(CLUBS, THREE, false),
                new CommunityCard(CLUBS, FOUR, false),
                new CommunityCard(CLUBS, FIVE, false)
            )

            val comCards = new CommunityCardsInterface {
                override def getCards: Vector[CommunityCardInterface] = Cards
                override def revealNext: CommunityCardsInterface = this
                override def toString(): String = ""
                override def equals(obj: Any): Boolean = false}

            val rankedCards = CardEvaluator.apply().rankCards(players, comCards)
            rankedCards(0).highestCard should be(ACE)
            rankedCards(0).handRank should be(STRAIGHT_FLUSH)
            rankedCards(0).player should be(player1)
        }
        "correctly rank cards with two pair" in {
            val player1 = new Player(1,
                Hand((new Card(CLUBS, SIX), new Card(HEARTS, ACE))),
                1000, 0)
            val players = Vector[PlayerInterface](player1)
            
            val Cards: Vector[CommunityCard] = Vector(
                new CommunityCard(CLUBS, ACE, false),
                new CommunityCard(HEARTS, SIX, false),
                new CommunityCard(DIAMONDS, SEVEN, false),
                new CommunityCard(DIAMONDS, FOUR, false),
                new CommunityCard(HEARTS, QUEEN, false)
            )

            val comCards = new CommunityCardsInterface {
                override def getCards: Vector[CommunityCardInterface] = Cards
                override def revealNext: CommunityCardsInterface = this
                override def toString(): String = ""
                override def equals(obj: Any): Boolean = false}

            val rankedCards = CardEvaluator.apply().rankCards(players, comCards)
            rankedCards(0).highestCard should be(ACE)
            rankedCards(0).handRank should be(TWO_PAIR)
            rankedCards(0).player should be(player1)
        }
    }
}
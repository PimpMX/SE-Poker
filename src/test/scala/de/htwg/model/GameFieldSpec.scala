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
import de.htwg.model.gameFieldComponent.cardEvaluatorBaseImpl.CardEvaluatorFactory



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
        "correctly rank cards with High-Cards" in {
            val player1 = new Player(1,
                Hand((new Card(CLUBS, SIX), new Card(HEARTS, ACE))),
                1000, 0)
            val players = Vector[PlayerInterface](player1)
            
            val Cards: Vector[CommunityCard] = Vector(
                new CommunityCard(CLUBS, TWO, false),
                new CommunityCard(HEARTS, THREE, false),
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
            rankedCards(0).handRank should be(HIGH_CARD)
            rankedCards(0).player should be(player1)
        }
        "correctly returns Rank of Int" in {
            val rankedCards = CardEvaluator.apply().intToRank(0) should be(Some(TWO))
            val rankedCards1 = CardEvaluator.apply().intToRank(1) should be(Some(THREE))
            val rankedCards2 = CardEvaluator.apply().intToRank(2) should be(Some(FOUR))
            val rankedCards3 = CardEvaluator.apply().intToRank(3) should be(Some(FIVE))
            val rankedCards4 = CardEvaluator.apply().intToRank(4) should be(Some(SIX))
            val rankedCards5 = CardEvaluator.apply().intToRank(5) should be(Some(SEVEN))
            val rankedCards6 = CardEvaluator.apply().intToRank(6) should be(Some(EIGHT))
            val rankedCards7 = CardEvaluator.apply().intToRank(7) should be(Some(NINE))
            val rankedCards8 = CardEvaluator.apply().intToRank(8) should be(Some(TEN))
            val rankedCards9 = CardEvaluator.apply().intToRank(9) should be(Some(JACK))
            val rankedCards10 = CardEvaluator.apply().intToRank(10) should be(Some(QUEEN))
            val rankedCards11 = CardEvaluator.apply().intToRank(11) should be(Some(KING))
            val rankedCards12 = CardEvaluator.apply().intToRank(12) should be(Some(ACE))
            val rankedCards13 = CardEvaluator.apply().intToRank(13) should be(None)
        }

        "correctly returns Int of Handrank" in {
            val rankedCards = CardEvaluator.apply().handRankToInt(ROYAL_FLUSH) should be(9)
            val rankedCards1 = CardEvaluator.apply().handRankToInt(STRAIGHT_FLUSH) should be(8)
            val rankedCards2 = CardEvaluator.apply().handRankToInt(FOUR_OF_A_KIND) should be(7)
            val rankedCards3 = CardEvaluator.apply().handRankToInt(FULL_HOUSE) should be(6)
            val rankedCards4 = CardEvaluator.apply().handRankToInt(FLUSH) should be(5)
            val rankedCards5 = CardEvaluator.apply().handRankToInt(STRAIGHT) should be(4)
            val rankedCards6 = CardEvaluator.apply().handRankToInt(THREE_OF_A_KIND) should be(3)
            val rankedCards7 = CardEvaluator.apply().handRankToInt(TWO_PAIR) should be(2)
            val rankedCards8 = CardEvaluator.apply().handRankToInt(ONE_PAIR) should be(1)
            val rankedCards9 = CardEvaluator.apply().handRankToInt(HIGH_CARD) should be(0)
        }

        "correctly returns Int of Rank" in {
            val rankedCards = CardEvaluator.apply().rankToInt(TWO) should be(0)
            val rankedCards1 = CardEvaluator.apply().rankToInt(THREE) should be(1)
            val rankedCards2 = CardEvaluator.apply().rankToInt(FOUR) should be(2)
            val rankedCards3 = CardEvaluator.apply().rankToInt(FIVE) should be(3)
            val rankedCards4 = CardEvaluator.apply().rankToInt(SIX) should be(4)
            val rankedCards5 = CardEvaluator.apply().rankToInt(SEVEN) should be(5)
            val rankedCards6 = CardEvaluator.apply().rankToInt(EIGHT) should be(6)
            val rankedCards7 = CardEvaluator.apply().rankToInt(NINE) should be(7)
            val rankedCards8 = CardEvaluator.apply().rankToInt(TEN) should be(8)
            val rankedCards9 = CardEvaluator.apply().rankToInt(JACK) should be(9)
            val rankedCards10 = CardEvaluator.apply().rankToInt(QUEEN) should be(10)
            val rankedCards11 = CardEvaluator.apply().rankToInt(KING) should be(11)
            val rankedCards12 = CardEvaluator.apply().rankToInt(ACE) should be(12)
        }
    }
}
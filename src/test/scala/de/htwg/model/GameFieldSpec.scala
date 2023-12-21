package de.htwg.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import de.htwg.model.gameFieldComponent.gameFieldBaseImpl.GameField
import de.htwg.model.gameFieldComponent.gameFieldGenerator.GameGenerator   

class GameFieldSpec extends AnyWordSpec with Matchers  {

    "GameField" should {

        val gameGenerator = new GameGenerator

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

            gameField.get.activePlayerBet(100).get.getPlayerAtTurn.getBalance shouldBe(900)
            gameField.get.activePlayerBet(10000) shouldBe(Option.empty)
        }

        "have a working all-in method" in {
            val gameField = gameGenerator(1)
            gameField.isDefined shouldBe(true)
            gameField.get.activePlayerAllIn().isDefined shouldBe(true)
            gameField.get.activePlayerAllIn().get.activePlayerAllIn() shouldBe(Option.empty)
        }

        "have a working check method" in {
            val gameField = gameGenerator(1)
            gameField.isDefined shouldBe(true)
            gameField.get.activePlayerCheck().get.getPlayerAtTurn.getPlayerNum shouldBe(0)
        }

        "have a working fold method" in {
            val gameField = gameGenerator(1)
            gameField.isDefined shouldBe(true)
            gameField.get.activePlayerFold().isDefined shouldBe(true)
        }

        "have a working toString method" in {
            val gameField = gameGenerator(1)
            gameField.isDefined shouldBe(true)
            gameField.get.toString() shouldBe(gameField.get.toString())
        }
    }
}

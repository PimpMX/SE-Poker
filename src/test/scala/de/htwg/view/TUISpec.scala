package de.htwg.view

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.model._

class TUISpec extends AnyWordSpec with Matchers {

    "TUI" should {
        "produce a the correct scalable CLI view of the game state" in {
            val gameStateTwoPlayers: GameField = GameHandler.generateTwoPlayerGame()
            val gameStateThreePlayers: GameField = GameHandler.generateThreePlayerGame()

            TUI.produceCLIView(gameStateTwoPlayers) should be(TUI.produceCLIView(gameStateTwoPlayers))
            TUI.produceCLIView(gameStateTwoPlayers.switchToNextPlayer()) should be(TUI.produceCLIView(gameStateTwoPlayers.switchToNextPlayer()))
            
            TUI.produceCLIView(gameStateThreePlayers) should be(TUI.produceCLIView(gameStateThreePlayers))
            TUI.produceCLIView(gameStateThreePlayers.switchToNextPlayer()) should be(TUI.produceCLIView(gameStateThreePlayers.switchToNextPlayer()))
        }
    }
}


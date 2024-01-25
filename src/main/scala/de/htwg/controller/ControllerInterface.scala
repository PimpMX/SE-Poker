package de.htwg.controller

import de.htwg.util.Observable
import de.htwg.model.gameFieldComponent.GameFieldInterface

trait ControllerInterface extends Observable {
    
    def getGameState(): GameFieldInterface
    def setGameState(newGameState: GameFieldInterface): Unit
    def newGame(numPlayers: Int): Boolean
    def bet(amount: Int): Boolean
    def betAllIn(): Boolean
    def call(): Boolean
    def check(): Boolean
    def fold(): Boolean
    def undo(): Unit
    def redo(): Unit
    def exit(): Unit
}
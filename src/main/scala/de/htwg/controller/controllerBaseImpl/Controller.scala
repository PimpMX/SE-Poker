package de.htwg.controller.controllerBaseImpl

import de.htwg.view.TUI
import de.htwg.model.gameFieldComponent.GameFieldInterface
import de.htwg.util._
import de.htwg.controller.commands.NewGameCmd
import de.htwg.controller.commands.BetCmd
import de.htwg.controller.commands.BetAllInCmd
import de.htwg.controller.commands.CheckCmd
import de.htwg.controller.commands.FoldCmd
import de.htwg.controller.ControllerInterface

class Controller(var gameState: GameFieldInterface) extends ControllerInterface with Observable {

  private val undoManager = new UndoManager

  def getGameState(): GameFieldInterface = gameState

  def setGameState(newGameState: GameFieldInterface): Unit = {
    gameState = newGameState
    this.notifyObservers(Event.Move)
  } 

  def newGame(numPlayers: Int): Boolean = {
    val executed = undoManager.doStep(new NewGameCmd(this, numPlayers))
    if(executed) this.notifyObservers(Event.Move)
    executed
  }
  
  def bet(amount: Int): Boolean = {

    val executed = undoManager.doStep(new BetCmd(this, amount))
    if(executed) this.notifyObservers(Event.Move)
    executed
  }

  def betAllIn(): Boolean = {

    val executed = undoManager.doStep(new BetAllInCmd(this))
    if(executed) this.notifyObservers(Event.Move)
    executed
  }

  def check(): Boolean = {
      
    val executed = undoManager.doStep(new CheckCmd(this))
    if(executed) this.notifyObservers(Event.Move)
    executed
  }

  def fold(): Boolean = {
    
    val executed = undoManager.doStep(new FoldCmd(this))
    if(executed) this.notifyObservers(Event.Move)
    executed
  }

  def undo(): Unit = {
    undoManager.undoStep
    this.notifyObservers(Event.Move)
  }

  def redo(): Unit = {
    undoManager.redoStep
    this.notifyObservers(Event.Move)
  }

  def exit(): Unit = {
    this.notifyObservers(Event.Quit)
  }
  
  override def toString(): String = gameState.toString()
}
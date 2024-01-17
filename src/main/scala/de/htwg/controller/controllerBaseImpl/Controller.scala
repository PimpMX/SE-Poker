package de.htwg.controller.controllerBaseImpl

import de.htwg.util._
import de.htwg.controller.commands._
import com.google.inject.Guice
import de.htwg.model.gameFieldComponent._
import de.htwg.controller.ControllerInterface
import de.htwg.TexasHoldEmModule

class Controller extends ControllerInterface with Observable {

  val injector = Guice.createInjector(new TexasHoldEmModule)
  val gameGenerator = injector.getInstance(classOf[GameGeneratorInterface])

  private var gameState = gameGenerator(2).get.startOfRound
  private val undoManager = new UndoManager

  def getGameState(): GameFieldInterface = gameState

  def setGameState(newGameState: GameFieldInterface): Unit = {
    gameState = newGameState
  } 

  def newGame(numPlayers: Int): Boolean = {
    val executed = undoManager.doStep(new NewGameCmd(this, numPlayers))
    if(executed) this.notifyObservers(Move)
    executed
  }
  
  def bet(amount: Int): Boolean = {

    val executed = undoManager.doStep(new BetCmd(this, amount))
    if(executed) this.notifyObservers(Move)
    executed
  }

  def betAllIn(): Boolean = {

    val executed = undoManager.doStep(new BetAllInCmd(this))
    if(executed) this.notifyObservers(Move)
    executed
  }

  def check(): Boolean = {
      
    val executed = undoManager.doStep(new CheckCmd(this))
    if(executed) this.notifyObservers(Move)
    executed
  }

  def fold(): Boolean = {
    
    val executed = undoManager.doStep(new FoldCmd(this))
    if(executed) this.notifyObservers(Move)
    executed
  }

  def undo(): Unit = {
    undoManager.undoStep
    this.notifyObservers(Move)
  }

  def redo(): Unit = {
    undoManager.redoStep
    this.notifyObservers(Move)
  }

  def exit(): Unit = {
    this.notifyObservers(Quit)
  }
  
  override def toString(): String = gameState.toString()
}
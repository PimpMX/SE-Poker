package de.htwg.controller.commands

import de.htwg.util.Command
import de.htwg.model.GameField
import de.htwg.controller.Controller

class NewGameCmd(controller: Controller, amountPlayers: Int) extends Command {
  
  var memento: GameField = controller.gameState

  override def doStep: Boolean =  {

    memento = controller.gameState
    val newGameState = GameField(amountPlayers)

    if(newGameState.isDefined) {
      controller.gameState = newGameState.get
      true
    } else {
      false
    }
  }

  override def undoStep: Unit = {
    val tmp = controller.gameState
    controller.gameState = memento
    memento = tmp
  }
  
  override def redoStep: Unit = {
    val tmp = controller.gameState
    controller.gameState = memento
    memento = tmp
  }
}
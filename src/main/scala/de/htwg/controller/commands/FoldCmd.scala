package de.htwg.controller.commands

import de.htwg.util.Command
import de.htwg.controller.Controller
import de.htwg.model.GameField

class FoldCmd(controller: Controller) extends Command {
  
  var memento: GameField = controller.gameState

  override def doStep: Boolean =  {

    memento = controller.gameState
    val newGameState = controller.gameState.activePlayerFold()

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

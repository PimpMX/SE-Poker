package de.htwg.controller.commands

import de.htwg.util.Command
import de.htwg.model.gameFieldComponent.GameFieldInterface
import de.htwg.model.gameFieldComponent.gameFieldGenerator.GameGenerator
import de.htwg.controller.ControllerInterface

class NewGameCmd(controller: ControllerInterface, amountPlayers: Int) extends Command {
  
  var memento: GameFieldInterface = controller.getGameState()

  override def doStep: Boolean =  {

    memento = controller.getGameState()
    val newGameState = GameGenerator(amountPlayers)

    if(newGameState.isDefined) {
      controller.setGameState(newGameState.get)
      true
    } else {
      false
    }
  }

  override def undoStep: Unit = {
    val tmp = controller.getGameState()
    controller.setGameState(memento)
    memento = tmp
  }
  
  override def redoStep: Unit = {
    val tmp = controller.getGameState()
    controller.setGameState(memento)
    memento = tmp
  }
}
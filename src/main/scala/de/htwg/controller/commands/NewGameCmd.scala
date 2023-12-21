package de.htwg.controller.commands

import de.htwg.util.Command
import de.htwg.model.gameFieldComponent.GameFieldInterface
import de.htwg.model.gameFieldComponent.gameFieldGenerator.GameGenerator
import de.htwg.controller.ControllerInterface
import com.google.inject.Guice
import de.htwg.TexasHoldEmModule
import de.htwg.model.gameFieldComponent._

class NewGameCmd(controller: ControllerInterface, amountPlayers: Int) extends Command {
  
  val injector = Guice.createInjector(new TexasHoldEmModule)
  val gameGenerator = injector.getInstance(classOf[GameGeneratorInterface])

  var memento: GameFieldInterface = controller.getGameState()

  override def doStep: Boolean =  {

    memento = controller.getGameState()
    val newGameState = gameGenerator(amountPlayers)

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
package de.htwg

import de.htwg.controller.controllerBaseImpl.Controller
import de.htwg.model.gameFieldComponent.gameFieldGenerator.GameGenerator
import de.htwg.view.TUI
import de.htwg.view.GUI
import de.htwg.util._

object TexasHoldEm {

  def main(args: Array[String]): Unit = {

    val controller = new Controller(GameGenerator(4).get)
    val tui = new TUI(controller)
    val gui = new GUI(controller)
    
    controller.notifyObservers(Event.Move)
    tui.gameLoop()
  }
}

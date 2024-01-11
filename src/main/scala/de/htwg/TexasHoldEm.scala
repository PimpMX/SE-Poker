package de.htwg

import de.htwg.controller._
import de.htwg.model.gameFieldComponent._
import de.htwg.view.TUI
import de.htwg.view.GUI
import de.htwg.util._
import com.google.inject.Guice
import de.htwg.model.fileIoComponent.fileIoJsonImpl.FileIO

object TexasHoldEm {

  def main(args: Array[String]): Unit = {

    val injector = Guice.createInjector(new TexasHoldEmModule)
    val controller = injector.getInstance(classOf[ControllerInterface])

    val fio: FileIO = new FileIO
    fio.save(controller.getGameState())

    controller.getGameState()
    
    val tui = new TUI(controller)
    val gui = new GUI(controller)

    controller.notifyObservers(Move)
    tui.gameLoop()
  }
}

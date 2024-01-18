package de.htwg

import de.htwg.controller._
import de.htwg.model.gameFieldComponent._
import de.htwg.view.TUI
import de.htwg.view.GUI
import de.htwg.util._
import com.google.inject.Guice
import de.htwg.model.fileIoComponent.fileIoXmlImpl.XMLFileIO
import scala.util.Try
import scala.util.Success
import scala.util.Failure

object TexasHoldEm {

  def main(args: Array[String]): Unit = {

    val injector = Guice.createInjector(new TexasHoldEmModule)
    val controller = injector.getInstance(classOf[ControllerInterface])
    
    val tui = new TUI(controller)
    val gui: Try[GUI] = Try(new GUI(controller))

    gui match {
      case Success(e) => {} 
      case Failure(e) => println("[INFO] Starting without GUI")
    }

    controller.notifyObservers(Move)
    tui.gameLoop()
  }
}

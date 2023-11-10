package de.htwg.view

import de.htwg.util._
import de.htwg.controller._

class TUI(controller: Controller) extends Observer {

  controller.add(this)

  override def update(e: Event): Unit = {
    e match {
      case Event.Quit => System.exit(0)
      case Event.Move => println(controller.toString)
    }
  }

  def gameLoop(): Unit = {

    while(true) {
      
      val input = scala.io.StdIn.readLine()
      
      if(!userCmd(input))
        println("Invalid command")
    }
  }

  def userCmd(input: String): Boolean = {

    input match {
      case "new game" => 
        controller.restart_game()
        true
      case bet if bet.startsWith("bet ") && bet.substring(4).forall(_.isDigit) => 
        controller.bet(bet.substring(4).toInt)
        true
      case "bet all-in" =>
        controller.bet_all_in()
        true
      case "check" => 
        controller.check()
        true
      case "fold" => 
        controller.fold()
        true
      case _ =>
        false
    }
  }
}

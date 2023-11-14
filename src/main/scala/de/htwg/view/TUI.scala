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
      val cmd = userCmd(input)

      if(!cmd._1)
        println(cmd._2.get)
    }
  }

  def userCmd(input: String): (Boolean, Option[String]) = {

    input match {
      
      case command if command.startsWith("new game ") && command.substring(9).forall(_.isDigit) =>
        
        if(controller.newGame(command.substring(9).toInt)) {
          (true, Option.empty)
        } else {
          (false, Option("Number should be in range 1-10"))
        }
      
      case bet if bet.startsWith("bet ") && bet.substring(4).forall(_.isDigit) => 
        
        if(controller.bet(bet.substring(4).toInt)) {
          (true, Option.empty)
        } else {
          (false, Option("Not enough money"))
        }

      case "bet all-in" =>

        if(controller.betAllIn()) {
          (true, Option.empty)
        } else {
          (false, Option("No money left to bet"))
        }
      
      case "check" => 
        
        if(controller.check()) {
          (true, Option.empty)
        } else {
          (false, Option("Cannot check right now"))
        }

      case "fold" =>

        controller.fold()
        (true, Option.empty)
      
      case "exit" =>
      
        controller.exit()
        (true, Option.empty)
      
      case _ =>
        (false, Option("Invalid command"))
    }
  }
}

package de.htwg.view

import de.htwg.util._
import de.htwg.controller._
import scala.util.Try
import scala.util.Success
import scala.util.Failure

class TUI(controller: Controller) extends Observer {

  controller.add(this)

  override def update(e: Event): Unit = {
    e match {
      case Event.Quit => System.exit(0)
      case Event.Move => println(controller.toString)
    }
  }

  def gameLoop(): Unit = {

    while (true) {
      val input = scala.io.StdIn.readLine()
      val cmd = userCmd(input)

      cmd match {
        case Success(_) => // Keine Probleme
        case Failure(exception) => println(exception.getMessage)
      }
    }
  }

  def userCmd(input: String): Try[Unit] = {

    input match {

      case command if command.startsWith("new game ") && command.substring(9).forall(_.isDigit) =>
        if (!controller.newGame(command.substring(9).toInt))
          Failure(new IllegalArgumentException("Number should be in range 1-10"))

      case bet if bet.startsWith("bet ") && bet.substring(4).forall(_.isDigit) =>
        if (!controller.bet(bet.substring(4).toInt))
          Failure(new IllegalArgumentException("Not enough money"))
        
      case "bet all-in" =>
        if (!controller.betAllIn())
          Failure(new IllegalArgumentException("No money left to bet"))

      case "check" =>
        if (!controller.check())
          Failure(new IllegalArgumentException("Cannot check right now"))

      case "fold" =>
        controller.fold()

      case "undo" =>
        controller.undo()

      case "redo" =>
        controller.redo()

      case "exit" =>
        controller.exit()

      case _ =>
        Failure(new IllegalArgumentException("Invalid command"))
    }
  }
}

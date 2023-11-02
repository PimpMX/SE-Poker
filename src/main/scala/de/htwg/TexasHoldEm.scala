package de.htwg

import de.htwg.controller.Controller
import de.htwg.model.CommunityCard

object TexasHoldEm {

  def main(args: Array[String]): Unit = {
    while(true) {
      val input = scala.io.StdIn.readLine()
      println(Controller.userCmd(input))
    }
  }
}

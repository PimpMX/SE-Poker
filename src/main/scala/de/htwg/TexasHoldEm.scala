package de.htwg

import de.htwg.controller.Controller
import de.htwg.model.CardSet

object TexasHoldEm {

  @main
  def run(): Unit = {
    println(Controller.getCLIView)
    val cardSet = new CardSet()
    val cards = cardSet.takeCard()
    val cards2 = cardSet.takeCard()
  }
}

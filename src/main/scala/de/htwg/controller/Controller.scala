package de.htwg.controller

import de.htwg.view.TUI
import de.htwg.model.{GameField, GameHandler}
import de.htwg.util._

class Controller(var gameState: GameField) extends Observable {

  def restart_game(): Unit = {
    gameState = GameHandler.generateTwoPlayerGame()
    this.notifyObservers(Event.Move)
  }

  def bet(amount: Int): Unit = {
    gameState = gameState.switchToNextPlayer()
    this.notifyObservers(Event.Move)
  }

  def bet_all_in(): Unit = {
    gameState = gameState.switchToNextPlayer()
    this.notifyObservers(Event.Move)
  }

  def check(): Unit = {
    gameState = gameState.switchToNextPlayer()
    this.notifyObservers(Event.Move)
  }

  def fold(): Unit = {
    gameState = gameState.switchToNextPlayer()
    this.notifyObservers(Event.Move)
  }
  
  override def toString(): String = gameState.toString()
}
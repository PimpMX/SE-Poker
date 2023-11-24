package de.htwg.controller

import de.htwg.view.TUI
import de.htwg.model.GameField
import de.htwg.util._

class Controller(var gameState: GameField) extends Observable {

  def newGame(numPlayers: Int): Boolean = {

    val generated = GameField.gameFactory(numPlayers)

    if(generated.isDefined) {
      gameState = generated.get
      this.notifyObservers(Event.Move)
      true
    } else {
      false
    }
  }
  
  def bet(amount: Int): Boolean = {

    val betted = gameState.activePlayerBet(amount)

    if(betted.isDefined) {
      gameState = betted.get
      this.notifyObservers(Event.Move)
      true
    } else {
      false
    }
  }

  def betAllIn(): Boolean = {

    val betted = gameState.activePlayerAllIn()

    if(betted.isDefined) {
      gameState = betted.get
      this.notifyObservers(Event.Move)
      true
    } else {
      false
    }
  }

  def check(): Boolean = {
      
    val checked = gameState.activePlayerCheck()

    if(checked.isDefined) {
      gameState = checked.get
      this.notifyObservers(Event.Move)
      true
    } else {
      false
    }
  }

  def fold(): Boolean = {
    
    val folded = gameState.activePlayerFold()

    if(folded.isDefined) {
      gameState = folded.get
      this.notifyObservers(Event.Move)
      true
    } else {
      false
    }
  }

  def exit(): Unit = {
    this.notifyObservers(Event.Quit)
  }
  
  override def toString(): String = gameState.toString()
}
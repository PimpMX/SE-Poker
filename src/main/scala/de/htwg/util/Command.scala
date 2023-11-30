package de.htwg.util

trait Command {
  def doStep: Boolean
  def undoStep: Unit
  def redoStep: Unit
};

package de.htwg.se.bohnanza.util

import de.htwg.se.bohnanza.controller.ControllerComponent.{IController}

abstract class Command(controller: IController) {
  val memento: GameMemento = GameMemento(controller.game, controller.phase)
  def doStep: Unit
  def undoStep: Unit = {
    controller.game = memento.game
    controller.phase = memento.phase
  }
  def redoStep: Unit = doStep
}

class UndoManager:
  var undoStack: List[Command] = Nil
  var redoStack: List[Command] = Nil

  def doStep(command: Command) = {
    undoStack = command :: undoStack
    command.doStep
    // can't redo if already did task after undo
    redoStack = Nil
  }

  def undoStep = {
    undoStack match {
      case Nil =>
      case head :: stack => {
        head.undoStep
        undoStack = stack
        redoStack = head :: redoStack
      }
    }
  }

  def redoStep = {
    redoStack match {
      case Nil =>
      case head :: stack => {
        head.redoStep
        redoStack = stack
        undoStack = head :: undoStack
      }
    }
  }

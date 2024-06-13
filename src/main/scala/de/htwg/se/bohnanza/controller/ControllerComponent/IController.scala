package de.htwg.se.bohnanza.controller.ControllerComponent

import bohnanza.model.*
import bohnanza.util.*
import java.util.Observer
import bohnanza.util.UndoManager
import de.htwg.se.bohnanza.model.PhaseStateComponent.*

trait IController extends Observable {
  var game: Game
  var phase: IPhaseState

  def createPlayer(playerName: String): Unit

  def undo(): Unit

  def redo(): Unit

  def draw(playerIndex: Int): Unit

  def plant(playerIndex: Int, beanFieldIndex: Int): Unit

  def nextPhase: Unit

  def harvest(playerIndex: Int, beanFieldIndex: Int): Unit

  def turn: Unit

  def take(playerIndex: Int, cardIndex: Int, beanFieldIndex: Int): Unit
}

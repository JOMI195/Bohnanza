package de.htwg.se.bohnanza.util

import de.htwg.se.bohnanza.model.*
import de.htwg.se.bohnanza.model.ArgsHandlerComponent.{HandlerResponse}

enum ObserverEvent {
  case PhaseChange
  case Plant
  case Harvest
  case Take
  case GameInfo
  case Draw // draw and turn for debugging purposes!!!
  case Turn
  case Undo
  case Redo
  case CreatePlayer
  case StartGame
}

trait Observer {
  def update(event: ObserverEvent): Unit
  def update(error: HandlerResponse): Unit
}

class Observable {
  var subscribers: Vector[Observer] = Vector()

  def add(s: Observer): Unit = subscribers = subscribers :+ s

  def remove(s: Observer): Unit = subscribers =
    subscribers.filterNot(o => o == s)

  def notifyObservers(event: ObserverEvent): Unit =
    subscribers.foreach(o => o.update(event))

  def notifyObservers(error: HandlerResponse): Unit =
    subscribers.foreach(o => o.update(error))
}

package de.htwg.se.bohnanza.util

import _root_.de.htwg.se.bohnanza.model.GameComponent.IGame
import _root_.de.htwg.se.bohnanza.model.PhaseStateComponent.{IPhaseState}

case class GameMemento(game: IGame, phase: IPhaseState)

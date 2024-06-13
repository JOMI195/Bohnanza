package de.htwg.se.bohnanza.util

import bohnanza.model.*
import de.htwg.se.bohnanza.model.PhaseStateComponent.{IPhaseState}

case class GameMemento(game: Game, phase: IPhaseState)

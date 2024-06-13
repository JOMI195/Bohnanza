package de.htwg.se.bohnanza.model.PhaseStateComponent

import de.htwg.se.bohnanza.model.GameComponent.IGame

trait IPhaseState {
  def nextPhase: IPhaseState
  // automatic steps that need to be done in that phase
  def startPhase(game: IGame): IGame = game
}

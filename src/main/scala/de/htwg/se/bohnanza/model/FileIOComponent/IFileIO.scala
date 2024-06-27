package de.htwg.se.bohnanza.model.FileIOComponent

import de.htwg.se.bohnanza.model.GameComponent.IGame
import de.htwg.se.bohnanza.model.PhaseStateComponent.IPhaseState
import de.htwg.se.bohnanza.model.PhaseStateComponent.GameInitializationPhase

trait IFileIO {

  def load: (IGame, IPhaseState)
  def save(game: IGame, phase: IPhaseState): Unit
}

package de.htwg.se.bohnanza.model.FileIOComponent

import de.htwg.se.bohnanza.model.GameComponent.IGame
import de.htwg.se.bohnanza.model.PhaseStateComponent.IPhaseState
import de.htwg.se.bohnanza.model.PhaseStateComponent.GameInitializationPhase

trait IFileIO {
  val SAVEGAMEDIR = "./savegame/"

  /** Loads the saved game state from the savegames folder. */
  def load: (IGame, IPhaseState)

  /** Saves the current game state in the savegames folder. */
  def save(game: IGame, phase: IPhaseState): Unit
}

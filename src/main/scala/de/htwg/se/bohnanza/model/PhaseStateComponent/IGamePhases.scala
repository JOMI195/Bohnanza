package de.htwg.se.bohnanza.model.PhaseStateComponent

trait IDrawCardsPhase extends IPhaseState {
  def nextPhase: IPlayCardPhase
}

trait IGameInitializationPhase extends IPhaseState {
  def nextPhase: IPlayCardPhase
}

trait IPlayCardPhase extends IPhaseState {
  def nextPhase: ITradeAndPlantPhase
}

trait ITradeAndPlantPhase extends IPhaseState {
  def nextPhase: IDrawCardsPhase
}

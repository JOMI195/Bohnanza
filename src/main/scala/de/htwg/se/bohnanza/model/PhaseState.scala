package bohnanza.model

trait PhaseState {
  def nextPhase: PhaseState
  // automatic steps that need to be done in that phase
  def startPhase(game: Game): Game = game
}

class GameInitializationPhase extends PhaseState {
  def nextPhase: PhaseState = PlayCardPhase()
}

class PlayCardPhase extends PhaseState {
  override def toString(): String = {
    "Phase: PlayCardPhase\n" +
      "The allowed methods are:\n" +
      " - harvest\n" +
      " - plant\n" +
      " - draw\n" +
      " - turn\n"
  }
  def nextPhase: PhaseState = TradeAndPlantPhase()
  override def startPhase(game: Game): Game = {
    val updatedPlayerIndex = (game.currentPlayerIndex + 1) % game.players.length
    game.copy(currentPlayerIndex = updatedPlayerIndex)
  }
}

class TradeAndPlantPhase extends PhaseState {
  override def toString(): String = {
    "Phase: TradeAndPlantPhase\n" +
      "The allowed methods are:\n" +
      " - harvest\n" +
      " - plant\n"
  }
  def nextPhase: PhaseState = DrawCardsPhase()
  override def startPhase(game: Game): Game = game.drawCardToTurnOverField()
}

class DrawCardsPhase extends PhaseState {
  override def toString(): String = {
    "No method is allowed here, because everything is done automatically for you. :)\n"
  }
  def nextPhase: PhaseState = PlayCardPhase()
  override def startPhase(game: Game): Game =
    game.playerDrawCardFromDeck(game.currentPlayerIndex)

}

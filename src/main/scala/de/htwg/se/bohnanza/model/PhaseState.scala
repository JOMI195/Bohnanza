package bohnanza.model

trait PhaseState {
  def nextPhase: PhaseState
  // automatic steps that need to be done in that phase
  def startPhase(game: Game): Game
}

class PlayCardPhase extends PhaseState {
  def nextPhase: PhaseState = TradeAndPlantPhase()
  override def startPhase(game: Game): Game = {
    val updatedPlayerIndex = (game.currentPlayerIndex + 1) % game.players.length
    game.copy(currentPlayerIndex = updatedPlayerIndex)
  }
}

class TradeAndPlantPhase extends PhaseState {
  def nextPhase: PhaseState = DrawCardsPhase()
  override def startPhase(game: Game): Game = game.drawCardToTurnOverField()
}

class DrawCardsPhase extends PhaseState {
  def nextPhase: PhaseState = PlayCardPhase()
  override def startPhase(game: Game): Game =
    game.playerDrawCardFromDeck(game.currentPlayerIndex)

}

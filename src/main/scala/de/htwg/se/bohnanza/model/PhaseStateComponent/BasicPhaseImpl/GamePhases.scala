package de.htwg.se.bohnanza.model.PhaseStateComponent

import de.htwg.se.bohnanza.model.Game

class DrawCardsPhase extends IDrawCardsPhase {
  override def toString(): String = {
    "No method except next is allowed here, because everything is done automatically for you. :)\n"
  }
  def nextPhase: IPlayCardPhase = PlayCardPhase()
  override def startPhase(game: Game): Game =
    game.playerDrawCardFromDeck(game.currentPlayerIndex)
}

class GameInitializationPhase extends IGameInitializationPhase {
  def nextPhase: IPlayCardPhase = PlayCardPhase()
  override def startPhase(game: Game): Game = game
}

class TradeAndPlantPhase extends ITradeAndPlantPhase {
  override def toString(): String = {
    "Phase: TradeAndPlantPhase\n" +
      "The allowed methods are:\n" +
      " - harvest\n" +
      " - plant\n"
  }
  def nextPhase: IDrawCardsPhase = DrawCardsPhase()
  override def startPhase(game: Game): Game = game.drawCardToTurnOverField()
}

class PlayCardPhase extends IPlayCardPhase {
  override def toString(): String = {
    "Phase: PlayCardPhase\n" +
      "The allowed methods are:\n" +
      " - harvest\n" +
      " - plant\n" +
      " - draw\n" +
      " - turn\n"
  }
  def nextPhase: ITradeAndPlantPhase = TradeAndPlantPhase()
  override def startPhase(game: Game): Game = {
    val updatedPlayerIndex = (game.currentPlayerIndex + 1) % game.players.length
    game.copy(currentPlayerIndex = updatedPlayerIndex)
  }
}

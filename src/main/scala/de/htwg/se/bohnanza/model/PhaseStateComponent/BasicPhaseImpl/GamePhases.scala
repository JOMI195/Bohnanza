package de.htwg.se.bohnanza.model.PhaseStateComponent

import de.htwg.se.bohnanza.model.GameComponent.IGame
import com.google.inject.Inject

class DrawCardsPhase @Inject() extends IDrawCardsPhase {
  override def toString(): String = {
    "No method except next is allowed here, because everything is done automatically for you. :)\n"
  }
  def nextPhase: IPlayCardPhase = PlayCardPhase()
  override def startPhase(game: IGame): IGame =
    game.playerDrawCardFromDeck(game.currentPlayerIndex)
}

class GameInitializationPhase @Inject() extends IGameInitializationPhase {
  def nextPhase: IPlayCardPhase = PlayCardPhase()
  override def startPhase(game: IGame): IGame = game
}

class TradeAndPlantPhase @Inject() extends ITradeAndPlantPhase {
  override def toString(): String = {
    "Phase: TradeAndPlantPhase\n" +
      "The allowed methods are:\n" +
      " - harvest\n" +
      " - plant\n"
  }
  def nextPhase: IDrawCardsPhase = DrawCardsPhase()
  override def startPhase(game: IGame): IGame = game.drawCardToTurnOverField()
}

class PlayCardPhase @Inject() extends IPlayCardPhase {
  override def toString(): String = {
    "Phase: PlayCardPhase\n" +
      "The allowed methods are:\n" +
      " - harvest\n" +
      " - plant\n" +
      " - draw\n" +
      " - turn\n"
  }
  def nextPhase: ITradeAndPlantPhase = TradeAndPlantPhase()
  override def startPhase(game: IGame): IGame = {
    val updatedPlayerIndex = (game.currentPlayerIndex + 1) % game.players.length
    game.copy(currentPlayerIndex = updatedPlayerIndex)
  }
}

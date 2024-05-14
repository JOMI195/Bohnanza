package bohnanza.controller

import bohnanza.model.*
import bohnanza.util.*

class Controller(var game: Game, var phase: PhaseState = PlayCardPhase())
    extends Observable {
  val playerIndexHandler = PlayerIndexHandler(None)
  val argumentHandler = MethodHandler(Option(playerIndexHandler))

  def draw(playerIndex: Int): Unit = {
    val response = argumentHandler.checkOrDelegate(
      args = Map(
        HandlerKey.Method.key -> "draw",
        HandlerKey.PlayerFieldIndex.key -> playerIndex
      ),
      phase = phase,
      game = game
    )
    if (response == HandlerResponse.Success) {
      game = game.playerDrawCardFromDeck(playerIndex = playerIndex)
      notifyObservers
      return
    }

    notifyObservers
  }

  def plant(playerIndex: Int, beanFieldIndex: Int): Unit = {
    game = game.playerPlantCardFromHand(playerIndex, beanFieldIndex)
    notifyObservers
  }

  def nextPhase: Unit = {
    phase = phase.nextPhase
    game = phase.startPhase(game)
    notifyObservers
  }

  def harvest(playerIndex: Int, beanFieldIndex: Int): Unit = {
    // val response = methodhandler(argumente)
    game = game.playerHarvestField(playerIndex, beanFieldIndex)
    notifyObservers
  }

  def turn: Unit = {
    game = game.drawCardToTurnOverField()
    notifyObservers
  }

  def take(playerIndex: Int, cardIndex: Int, beanFieldIndex: Int): Unit = {
    game = game.playerPlantFromTurnOverField(
      playerIndex,
      cardIndex,
      beanFieldIndex
    )
    notifyObservers
  }
}

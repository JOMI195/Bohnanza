package bohnanza.controller

import bohnanza.model.*
import bohnanza.util.*
import java.util.Observer

class Controller(var game: Game, var phase: PhaseState = PlayCardPhase())
    extends Observable {
  val takeInvalidPlantHandler = TakeInvalidPlantHandler(None)
  val invalidPlantHandler = InvalidPlantHandler(Option(takeInvalidPlantHandler))
  val turnOverFieldIndexHandler = TurnOverFieldIndexHandler(
    Option(invalidPlantHandler)
  )
  val beanFieldIndexHandler = BeanFieldIndexHandler(
    Option(turnOverFieldIndexHandler)
  )
  val playerIndexHandler = PlayerIndexHandler(Option(beanFieldIndexHandler))
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
      notifyObservers(ObserverEvent.Draw)
      return
    }

    notifyObservers(response)
  }

  def plant(playerIndex: Int, beanFieldIndex: Int): Unit = {
    val response = argumentHandler.checkOrDelegate(
      args = Map(
        HandlerKey.Method.key -> "plant",
        HandlerKey.PlayerFieldIndex.key -> playerIndex,
        HandlerKey.BeanFieldIndex.key -> beanFieldIndex
      ),
      phase = phase,
      game = game
    )

    if (response == HandlerResponse.Success) {
      game = game.playerPlantCardFromHand(playerIndex, beanFieldIndex)
      notifyObservers(ObserverEvent.Plant)
      return
    }

    notifyObservers(response)
  }

  def nextPhase: Unit = {
    phase = phase.nextPhase
    game = phase.startPhase(game)
    notifyObservers(ObserverEvent.PhaseChange)
  }

  def harvest(playerIndex: Int, beanFieldIndex: Int): Unit = {
    val response = argumentHandler.checkOrDelegate(
      args = Map(
        HandlerKey.Method.key -> "harvest",
        HandlerKey.PlayerFieldIndex.key -> playerIndex,
        HandlerKey.BeanFieldIndex.key -> beanFieldIndex
      ),
      phase = phase,
      game = game
    )

    if (response == HandlerResponse.Success) {
      game = game.playerHarvestField(playerIndex, beanFieldIndex)
      notifyObservers(ObserverEvent.Harvest)
      return
    }

    notifyObservers(response)
  }

  def turn: Unit = {
    val response = argumentHandler.checkOrDelegate(
      args = Map(
        HandlerKey.Method.key -> "turn"
      ),
      phase = phase,
      game = game
    )

    if (response == HandlerResponse.Success) {
      game = game.drawCardToTurnOverField()
      notifyObservers(ObserverEvent.Turn)
      return
    }

    notifyObservers(response)
  }

  def take(playerIndex: Int, cardIndex: Int, beanFieldIndex: Int): Unit = {
    val response = argumentHandler.checkOrDelegate(
      args = Map(
        HandlerKey.Method.key -> "take",
        HandlerKey.PlayerFieldIndex.key -> playerIndex,
        HandlerKey.BeanFieldIndex.key -> beanFieldIndex,
        HandlerKey.TurnOverFieldIndex.key -> cardIndex
      ),
      phase = phase,
      game = game
    )

    if (response == HandlerResponse.Success) {
      game = game.playerPlantFromTurnOverField(
        playerIndex,
        cardIndex,
        beanFieldIndex
      )
      notifyObservers(ObserverEvent.Take)
      return
    }

    notifyObservers(response)
  }
}

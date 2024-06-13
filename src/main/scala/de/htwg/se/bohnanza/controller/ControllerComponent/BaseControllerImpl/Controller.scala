package de.htwg.se.bohnanza.controller.ControllerComponent

import de.htwg.se.bohnanza.model.PhaseStateComponent.*
import de.htwg.se.bohnanza.model.ArgsHandlerComponent.*
import de.htwg.se.bohnanza.model.GameComponent.*
import de.htwg.se.bohnanza.util.*

class Controller(
    var game: IGame,
    var phase: IPhaseState = GameInitializationPhase()
) extends IController {

  val undoManager = new UndoManager
  val argumentHandler: IArgumentHandler = new ArgumentHandler();

  def createPlayer(playerName: String): Unit = {
    val response = argumentHandler.checkOrDelegate(
      args = Map(
        HandlerKey.Method.key -> "createPlayer"
      ),
      phase = phase,
      game = game
    )

    if (response == HandlerResponse.Success) {
      undoManager.doStep(PlayerCreationCommand(this, playerName))
      notifyObservers(ObserverEvent.CreatePlayer)
      return
    }

    notifyObservers(response)
  }

  def undo(): Unit = {
    undoManager.undoStep
    notifyObservers(ObserverEvent.Undo)
  }

  def redo(): Unit = {
    undoManager.redoStep
    notifyObservers(ObserverEvent.Redo)
  }

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
      undoManager.doStep(DrawCommand(this, playerIndex))
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
      undoManager.doStep(PlantCommand(this, playerIndex, beanFieldIndex))
      notifyObservers(ObserverEvent.Plant)
      return
    }

    notifyObservers(response)
  }

  def nextPhase: Unit = {
    val response = argumentHandler.checkOrDelegate(
      args = Map(
        HandlerKey.Method.key -> "next"
      ),
      phase = phase,
      game = game
    )

    if (response == HandlerResponse.Success) {
      phase match {
        case _: GameInitializationPhase => {
          undoManager.doStep(NextPhaseCommand(this))
          notifyObservers(ObserverEvent.StartGame)
        }
        case _ =>
          undoManager.doStep(NextPhaseCommand(this))
          notifyObservers(ObserverEvent.PhaseChange)
      }
      return
    }

    notifyObservers(response)
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
      undoManager.doStep(HarvestCommand(this, playerIndex, beanFieldIndex))
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
      undoManager.doStep(TurnCommand(this))
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
      undoManager.doStep(
        TakeCommand(this, playerIndex, cardIndex, beanFieldIndex)
      )
      notifyObservers(ObserverEvent.Take)
      return
    }

    notifyObservers(response)
  }
}

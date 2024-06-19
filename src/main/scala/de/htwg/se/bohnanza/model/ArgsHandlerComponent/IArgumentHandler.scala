package de.htwg.se.bohnanza.model.ArgsHandlerComponent

import de.htwg.se.bohnanza.model.PhaseStateComponent.*
import de.htwg.se.bohnanza.model.GameComponent.IGame

enum HandlerKey(val key: String) {
  case PlayerFieldIndex extends HandlerKey("playerIndex")
  case BeanFieldIndex extends HandlerKey("beanFieldIndex")
  case TurnOverFieldIndex extends HandlerKey("turnOverFieldIndex")
  case Method extends HandlerKey("method")
  case HandIndex extends HandlerKey("handIndex")
}

enum HandlerResponse {
  case BeanFieldIndexError
  case PlayerIndexError
  case CurrentPlayerIndexError
  case TurnOverFieldIndexError
  case TakeInvalidPlantError
  case InvalidPlantError
  case MissingPlayerCreationError
  case HandIndexError
  case MethodError
  case ArgsError
  case Success
}

trait IArgumentHandler {
  def checkOrDelegate(
      args: Map[String, Any],
      phase: IPhaseState,
      game: IGame
  ): HandlerResponse
}

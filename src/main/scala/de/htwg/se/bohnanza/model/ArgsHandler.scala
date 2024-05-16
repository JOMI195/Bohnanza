package bohnanza.model

import scala.util.Try

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
  case HandIndexError
  case MethodError
  case ArgsError
  case Success
}

trait HandlerTemplate {
  val next: Option[HandlerTemplate]
  def check(
      args: Map[String, Any],
      phase: PhaseState,
      game: Game
  ): HandlerResponse

  def checkOrDelegate(
      args: Map[String, Any],
      phase: PhaseState,
      game: Game
  ): HandlerResponse = {
    val response = check(args, phase, game)
    if (response != HandlerResponse.Success) {
      return response
    }

    next match {
      case Some(handler) => handler.checkOrDelegate(args, phase, game)
      case None          => response
    }
  }
}

case class PlayerIndexHandler(next: Option[HandlerTemplate])
    extends HandlerTemplate {

  def check(
      args: Map[String, Any],
      phase: PhaseState,
      game: Game
  ): HandlerResponse = {
    val key = HandlerKey.PlayerFieldIndex.key
    // args can never be an integer since the Tui already checks that
    args.get(key) match {
      case Some(playerIndex) => {
        val checkedPlayerIndex = playerIndex.asInstanceOf[Int]
        if (
          checkedPlayerIndex >= game.players.length || checkedPlayerIndex < 0
        ) {
          return HandlerResponse.PlayerIndexError
        }
        if (checkedPlayerIndex != game.currentPlayerIndex) {
          return HandlerResponse.CurrentPlayerIndexError
        }
      }
      case None =>
    }
    return HandlerResponse.Success
  }
}

case class BeanFieldIndexHandler(next: Option[HandlerTemplate])
    extends HandlerTemplate {

  def check(
      args: Map[String, Any],
      phase: PhaseState,
      game: Game
  ): HandlerResponse = {
    val playerIndexKey = HandlerKey.PlayerFieldIndex.key
    val beanFieldIndexKey = HandlerKey.BeanFieldIndex.key
    args.get(playerIndexKey) match {
      case Some(playerIndex) => {
        val checkedPlayerIndex = playerIndex.asInstanceOf[Int]
        if (
          checkedPlayerIndex >= game.players.length || checkedPlayerIndex < 0
        ) {
          return HandlerResponse.PlayerIndexError
        }
        args.get(beanFieldIndexKey) match {
          case Some(beanFieldIndex) => {
            val checkedBeanFieldIndex = beanFieldIndex.asInstanceOf[Int]
            if (
              checkedBeanFieldIndex >= game
                .players(checkedPlayerIndex)
                .beanFields
                .length
              ||
              checkedBeanFieldIndex < 0
            ) {
              return HandlerResponse.BeanFieldIndexError
            }
          }
          case None =>
        }
      }
      case None =>
    }
    return HandlerResponse.Success
  }
}

case class TurnoverFieldIndexHandler(next: Option[HandlerTemplate])
    extends HandlerTemplate {

  def check(
      args: Map[String, Any],
      phase: PhaseState,
      game: Game
  ): HandlerResponse = {
    val turnoverFieldIndexKey = HandlerKey.TurnOverFieldIndex.key
    args.get(turnoverFieldIndexKey) match {
      case Some(turnoverFieldIndex) => {
        val checkedTurnoverFieldIndex = turnoverFieldIndex.asInstanceOf[Int]
        if (
          checkedTurnoverFieldIndex >= game.turnOverField.cards.length
          ||
          checkedTurnoverFieldIndex < 0
        ) {
          return HandlerResponse.TurnOverFieldIndexError
        }
      }
      case None =>
    }
    return HandlerResponse.Success
  }
}

case class MethodHandler(next: Option[HandlerTemplate])
    extends HandlerTemplate {
  override def check(
      args: Map[String, Any],
      phase: PhaseState,
      game: Game
  ): HandlerResponse = {
    args.get(HandlerKey.Method.key) match {
      case Some(method) => {
        phase match {
          case _: PlayCardPhase => {
            // remove draw, turn and take in future! Now only for debugging purposes!!!
            if (
              method == "harvest" || method == "plant" || method == "draw" || method == "turn" || method == "take"
            ) {
              return HandlerResponse.Success
            }
            return HandlerResponse.MethodError
          }
          case _: TradeAndPlantPhase => {
            if (method == "harvest" || method == "take") {
              return HandlerResponse.Success
            }
            return HandlerResponse.MethodError
          }
          case _: DrawCardsPhase => return HandlerResponse.MethodError
        }
      }
      case None => HandlerResponse.ArgsError
    }
  }
}

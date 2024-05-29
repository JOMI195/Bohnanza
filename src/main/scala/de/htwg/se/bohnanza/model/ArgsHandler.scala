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
  case TakeInvalidPlantError
  case InvalidPlantError
  case MissingPlayerCreationError
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

case class TakeInvalidPlantHandler(next: Option[HandlerTemplate])
    extends HandlerTemplate {

  def check(
      args: Map[String, Any],
      phase: PhaseState,
      game: Game
  ): HandlerResponse = {
    args.get(HandlerKey.Method.key) match {
      case Some("take") => {
        args.get(HandlerKey.TurnOverFieldIndex.key) match {
          case Some(turnOverFieldIndex) => {
            val checkedTurnoverFieldIndex = turnOverFieldIndex.asInstanceOf[Int]
            args.get(HandlerKey.PlayerFieldIndex.key) match {
              case Some(playerIndex) => {
                val checkedPlayerIndex = playerIndex.asInstanceOf[Int]
                args.get(HandlerKey.BeanFieldIndex.key) match {
                  case Some(beanFieldIndex) => {
                    val checkedBeanFieldIndex = beanFieldIndex.asInstanceOf[Int]
                    val beanOnBeanField = game
                      .players(checkedPlayerIndex)
                      .beanFields(checkedBeanFieldIndex)
                      .bean
                    val beanToPlant =
                      game.turnOverField.cards(checkedTurnoverFieldIndex)

                    beanOnBeanField match {
                      case Some(checkedBean) => {
                        if (checkedBean != beanToPlant) {
                          return HandlerResponse.TakeInvalidPlantError
                        }
                      }
                      case None =>
                    }
                  }
                  // all these can only happen if we forgot to pass the args correctly! Tui checks already, so they should be in the correct form!
                  case None => return HandlerResponse.ArgsError
                }
              }
              case None => return HandlerResponse.ArgsError
            }
          }
          case None => return HandlerResponse.ArgsError
        }
      }
      case None    => return HandlerResponse.ArgsError
      case Some(_) =>
    }
    return HandlerResponse.Success
  }
}

case class InvalidPlantHandler(next: Option[HandlerTemplate])
    extends HandlerTemplate {

  def check(
      args: Map[String, Any],
      phase: PhaseState,
      game: Game
  ): HandlerResponse = {
    args.get(HandlerKey.Method.key) match {
      case Some("plant") => {
        args.get(HandlerKey.PlayerFieldIndex.key) match {
          case Some(playerIndex) => {
            val checkedPlayerIndex = playerIndex.asInstanceOf[Int]
            args.get(HandlerKey.BeanFieldIndex.key) match {
              case Some(beanFieldIndex) => {
                val checkedBeanFieldIndex = beanFieldIndex.asInstanceOf[Int]
                val beanOnBeanField = game
                  .players(checkedPlayerIndex)
                  .beanFields(checkedBeanFieldIndex)
                  .bean
                val beanToPlant =
                  game.players(checkedPlayerIndex).hand.cards(0)

                beanOnBeanField match {
                  case Some(checkedBean) => {
                    if (checkedBean != beanToPlant) {
                      return HandlerResponse.InvalidPlantError
                    }

                  }
                  case None =>
                }
              }
              case None => return HandlerResponse.ArgsError
            }
          }
          case None => return HandlerResponse.ArgsError

        }
      }
      case None    => return HandlerResponse.ArgsError
      case Some(_) =>
    }
    return HandlerResponse.Success
  }
}

case class TurnOverFieldIndexHandler(next: Option[HandlerTemplate])
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
          case _: GameInitializationPhase => {
            if (
              method == "createPlayer" || (method == "next" && !game.players.isEmpty)
            ) {
              return HandlerResponse.Success
            } else if (method == "next" && game.players.isEmpty) {
              return HandlerResponse.MissingPlayerCreationError
            }
            return HandlerResponse.MethodError
          }
          case _: PlayCardPhase => {
            // remove draw, turn and take in future! Now only for debugging purposes!!!
            if (
              method == "next" || method == "harvest" || method == "plant" || method == "draw" || method == "turn" || method == "take"
            ) {
              return HandlerResponse.Success
            }
            return HandlerResponse.MethodError
          }
          case _: TradeAndPlantPhase => {
            if (method == "next" || method == "harvest" || method == "take") {
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

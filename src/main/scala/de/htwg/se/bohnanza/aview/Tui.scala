package bohnanza.aview

import bohnanza.model.{Game, HandlerResponse, Player}
import bohnanza.util.{Observer, ObserverEvent}
import bohnanza.controller.{Controller}
import scala.util.{Try, Success, Failure}
import bohnanza.model.PlayCardPhase
import bohnanza.model.TradeAndPlantPhase
import bohnanza.model.DrawCardsPhase

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input: String): Option[String] = {
    val splittedInput = input.split(" ").toList
    val info = Try(
      splittedInput.slice(1, splittedInput.length).map(i => i.toInt)
    )
    val command = splittedInput(0)
    command match {
      case "createPlayer" => {
        val msg = "Usage: createPlayer [playerName]"
        val playerName =
          splittedInput.slice(1, splittedInput.length).mkString(" ")

        playerName match {
          case "" => return Option("Invalid Input" + "\n" + msg)
          case _ => {
            controller.createPlayer(playerName)
            return None
          }
        }
      }

      case "undo" => {
        val msg = "Usage: undo"
        info match {
          case Success(checkedInfo) => {
            if (checkedInfo.isEmpty) {
              controller.undo()
              return None
            }
            return Option("Usage: undo")
          }
          case Failure(e) => {
            return Option("Invalid Input" + "\n" + msg)
          }
        }
      }

      case "redo" => {
        val msg = "Usage: redo"
        info match {
          case Success(checkedInfo) => {
            if (checkedInfo.isEmpty) {
              controller.redo()
              return None
            }
            return Option("Usage: redo")
          }
          case Failure(e) => {
            return Option("Invalid Input" + "\n" + msg)
          }
        }
      }

      // changes to the next phase
      // Usage: next
      case "next" => {
        val msg = "Usage: next"
        info match {
          case Success(checkedInfo) => {
            if (checkedInfo.isEmpty) {
              controller.nextPhase
              return None
            }
            return Option("Usage: next")
          }
          case Failure(e) => {
            return Option("Invalid Input" + "\n" + msg)
          }
        }
      }

      case "draw" => {
        val msg = "Usage: draw [playerIndex]"
        info match {
          case Success(checkedInfo) =>
            checkedInfo match {
              case playerIndex :: Nil =>
                controller.draw(playerIndex)
                None
              case _ =>
                Option(msg)
            }
          case Failure(e) =>
            Option("Invalid Input" + "\n" + msg)
        }
      }

      case "plant" => {
        val msg = "Usage: plant [playerIndex] [beanFieldIndex]"
        info match {
          case Success(checkedInfo) =>
            checkedInfo match {
              case playerIndex :: beanFieldIndex :: Nil =>
                controller.plant(playerIndex, beanFieldIndex)
                None
              case _ =>
                Option(msg)
            }
          case Failure(e) =>
            Option(
              "Invalid Input" + "\n" +
                msg
            )

        }
      }

      case "harvest" => {
        val msg = "Usage: harvest [playerIndex] [beanFieldIndex]"
        info match {
          case Success(checkedInfo) =>
            checkedInfo match {
              case playerIndex :: beanFieldIndex :: Nil =>
                controller.harvest(playerIndex, beanFieldIndex)
                None
              case _ =>
                Option(msg)
            }
          case Failure(e) =>
            Option(
              "Invalid Input" + "\n" +
                msg
            )

        }
      }

      // draw cards to TurnOverField
      // Usage: turn
      case "turn" => {
        val msg = "Usage: turn"
        info match {
          case Success(checkedInfo) => {
            if (checkedInfo.isEmpty) {
              controller.turn
              return None
            }
            return Option("Usage: turn")
          }
          case Failure(e) => {
            return Option("Invalid Input" + "\n" + msg)
          }
        }
      }

      // take cards from TurnOverField and plant them to a beanField
      case "take" => {
        val msg = "Usage: take [playerIndex] [cardIndex] [beanFieldIndex]"
        info match {
          case Success(checkedInfo) =>
            checkedInfo match {
              case playerIndex :: cardIndex :: beanFieldIndex :: Nil =>
                controller.take(playerIndex, cardIndex, beanFieldIndex)
                None
              case _ =>
                Option(msg)
            }
          case Failure(e) =>
            Option(
              "Invalid Input" + "\n" +
                msg
            )
        }
      }

      // case "cards"
      case "exit" => {
        Option("Exiting game...")
      }
      case _ => {
        Option("Command not recognized. Type 'help' for commands.")
      }
    }
  }

  override def update(error: HandlerResponse): Unit = {
    error match {
      case HandlerResponse.BeanFieldIndexError =>
        println("You don't have this bean field.\n")
      case HandlerResponse.PlayerIndexError =>
        println("This player does not exist.\n")
      case HandlerResponse.CurrentPlayerIndexError =>
        println("This player is not the current player.\n")
      case HandlerResponse.TurnOverFieldIndexError =>
        println("You can't access a turn-over card with this index.\n")
      case HandlerResponse.HandIndexError =>
        println("You don't have any cards to plant anymore.\n")
      case HandlerResponse.MethodError =>
        println("You can't use this method in this phase.\n")
      case HandlerResponse.ArgsError =>
        println("Debug: Argument error in controller and handler.\n")
      case HandlerResponse.Success =>
        println("Debug: Success should not be printed.\n")
      case HandlerResponse.MissingPlayerCreationError =>
        println(
          "You can't go to the next phase because you didn't create any player yet.\n"
        )
      case HandlerResponse.TakeInvalidPlantError =>
        println(
          "The bean from the turn over field does not match with the bean on your bean field.\n"
        )
      case HandlerResponse.InvalidPlantError =>
        println(
          "The bean from your hand does not match with the bean on your bean field.\n"
        )
    }
  }
  override def update(event: ObserverEvent): Unit = {
    event match {
      case ObserverEvent.PhaseChange => {
        println(controller.phase)
      }
      case ObserverEvent.Plant =>
        println(controller.game.players(controller.game.currentPlayerIndex))
      case ObserverEvent.Harvest =>
        println(controller.game.players(controller.game.currentPlayerIndex))
      case ObserverEvent.Take =>
        println(controller.game.players(controller.game.currentPlayerIndex))
      case ObserverEvent.GameInfo => println(controller.game)
      case ObserverEvent.Draw =>
        println(controller.game.players(controller.game.currentPlayerIndex))
      case ObserverEvent.Turn =>
        println(controller.game.turnOverField.toString() + "\n")
      case ObserverEvent.Undo         => println(controller.game)
      case ObserverEvent.Redo         => println(controller.game)
      case ObserverEvent.CreatePlayer => println(controller.game)
      case _                          =>

    }
  }

}

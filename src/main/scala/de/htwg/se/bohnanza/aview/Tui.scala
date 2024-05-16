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
      // case "start"

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
            println(e)
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
        println("You don't have this bean field.")
      case HandlerResponse.PlayerIndexError =>
        println("This player does not exist.")
      case HandlerResponse.CurrentPlayerIndexError =>
        println("This player is not the current player.")
      case HandlerResponse.TurnOverFieldIndexError =>
        println("You can't access a turn-over card with this index.")
      case HandlerResponse.HandIndexError =>
        println("You can't access a hand card with this index.")
      case HandlerResponse.MethodError =>
        println("You can't use this method in this phase.")
      case HandlerResponse.ArgsError =>
        println("Debug: Argument error in controller and handler.")
      case HandlerResponse.Success =>
        println("Debug: Success should not be printed.")
    }
  }
  override def update(event: ObserverEvent): Unit = {
    val currentPlayer =
      controller.game.players(controller.game.currentPlayerIndex)

    event match {
      case ObserverEvent.PhaseChange => {
        println(s"The phase changed to ${controller.phase}.")
        println("The allowed method is: ")
        controller.phase match {
          case _: PlayCardPhase => {
            println(" - harvest")
            println(" - plant")
            println(" - draw")
            println(" - turn")
          }
          case _: TradeAndPlantPhase => {
            println(" - harvest")
            println(" - plant")
          }
          case _: DrawCardsPhase =>
            println(
              "No method is allowed here,\n" +
                "because everything is done automatically for you. :)"
            )
        }
      }
      case ObserverEvent.Plant    => println(currentPlayer)
      case ObserverEvent.Harvest  => println(currentPlayer)
      case ObserverEvent.Take     => println(currentPlayer)
      case ObserverEvent.GameInfo => println(controller.game)
      case ObserverEvent.Draw     => println(currentPlayer)
      case ObserverEvent.Turn     => println(controller.game.turnOverField)
    }
  }

}

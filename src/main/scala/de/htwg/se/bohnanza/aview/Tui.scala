package bohnanza.aview

import bohnanza.model.Game
import bohnanza.util.{Observer}
import bohnanza.controller.{Controller}
import scala.util.{Try, Success, Failure}

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

      // take cards from TurnOverField
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

  override def update: Unit = println(controller.game)

}
package bohnanza.aview

import bohnanza.model.Game
import bohnanza.util.{Observer}
import bohnanza.controller.{Controller}
import scala.util.{Try, Success, Failure}

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input: String): Unit = {
    val splittedInput = input.split(" ").toList
    val info = Try(
      splittedInput.slice(1, splittedInput.length).map(i => i.toInt)
    )
    val command = splittedInput(0)
    command match {
      // case "start"

      // Usage: draw [playerIndex]
      case "draw" => {
        val msg = "Usage: draw [playerIndex]"
        info match {
          case Success(checkedInfo) =>
            checkedInfo match {
              case playerIndex :: Nil =>
                controller.draw(playerIndex)
              case _ =>
                println(msg)
            }
          case Failure(e) =>
            println(
              "Invalid Input" + "\n" +
                msg
            )
        }
      }

      // Usage: plant [playerIndex] [beanFieldIndex]
      case "plant" => {
        val msg = "Usage: plant [playerIndex] [beanFieldIndex]"
        info match {
          case Success(checkedInfo) =>
            checkedInfo match {
              case playerIndex :: beanFieldIndex :: Nil =>
                controller.plant(playerIndex, beanFieldIndex)
              case _ =>
                println(msg)
            }
          case Failure(e) =>
            println(
              "Invalid Input" + "\n" +
                msg
            )
        }
      }

      // Usage: harvest [playerIndex] [beanFieldIndex]
      case "harvest" => {
        val msg = "Usage: harvest [playerIndex] [beanFieldIndex]"
        info match {
          case Success(checkedInfo) =>
            checkedInfo match {
              case playerIndex :: beanFieldIndex :: Nil =>
                controller.harvest(playerIndex, beanFieldIndex)
              case _ =>
                println(msg)
            }
          case Failure(e) =>
            println(
              "Invalid Input" + "\n" +
                msg
            )
        }
      }

      // draw cards to TurnOverField
      // Usage: turn
      case "turn" => {
        controller.turn
      }

      // Usage: take [playerIndex] [cardIndex] [beanFieldIndex]
      // take cards from TurnOverField
      case "take" => {
        val msg = "Usage: take [playerIndex] [cardIndex] [beanFieldIndex]"
        info match {
          case Success(checkedInfo) =>
            checkedInfo match {
              case playerIndex :: cardIndex :: beanFieldIndex :: Nil =>
                controller.take(playerIndex, cardIndex, beanFieldIndex)
              case _ =>
                println(msg)
            }
          case Failure(e) =>
            println(
              "Invalid Input" + "\n" +
                msg
            )
        }
      }

      // case "cards"
      case "exit" => {
        println("Exiting game...")
      }
      case _ => {
        println("Command not recognized. Type 'help' for commands.")
      }
    }
  }

  override def update: Unit = println(controller.game)
}

package bohnanza.aview

import bohnanza.model.Game
import bohnanza.util.{Observer}
import bohnanza.controller.{Controller}

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input: String): Unit = {
    val splittedInput = input.split(" ").toList
    splittedInput(0) match {
      // case "start"

      // Usage: draw [playerIndex]
      case "draw" => {
        val playerIndex = splittedInput(1).toInt
        controller.draw(playerIndex)
      }

      // Usage: plantFromHand [playerIndex] [beanFieldIndex]
      case "plant" => {
        val playerIndex = splittedInput(1).toInt
        val beanFieldIndex = splittedInput(2).toInt
        controller.plant(playerIndex, beanFieldIndex)
      }

      // harvest [playerIndex] [beanFieldIndex]
      case "harvest" => {
        val playerIndex = splittedInput(1).toInt
        val beanFieldIndex = splittedInput(2).toInt
        controller.harvest(playerIndex, beanFieldIndex)
      }

      // turn
      // draw cards to TurnOverField
      case "turn" => {
        controller.turn
      }

      // take [playerIndex] [cardIndex] [beanFieldIndex]
      // take cards from TurnOverField
      case "take" => {
        val playerIndex = splittedInput(1).toInt
        val cardIndex = splittedInput(2).toInt
        val beanFieldIndex = splittedInput(3).toInt
        controller.take(
          playerIndex,
          cardIndex,
          beanFieldIndex
        )
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

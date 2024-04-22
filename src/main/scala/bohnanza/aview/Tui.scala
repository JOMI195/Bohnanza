package bohnanza.aview

import bohnanza.model.Game

class Tui {
  def processInputLine(input: String, game: Game): Game = {
    val splittedInput = input.split(" ")
    splittedInput[0] match {
        case "start"
        case "next"
        case "plant" => game.players[splittedInput[1]]
        case "harvest"
        case "cards"
        case _
    }
  }
}

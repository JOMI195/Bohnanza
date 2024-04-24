package bohnanza.aview

import bohnanza.model.Game

class Tui {
  def processInputLine(input: String, game: Game): Game = {
    val splittedInput = input.split(" ").toList
    splittedInput(0) match {
      // case "start"

      // draw [playerIndex]
      case "draw" => {
        val playerIndex = splittedInput(1).toInt
        game.playerDrawCardFromDeck(playerIndex)
      }
      // case "plant" => game.players[splittedInput[1]]
      // case "harvest"
      // case "take"
      // case "cards"
      // case _
    }
  }
}

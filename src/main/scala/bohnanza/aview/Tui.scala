package bohnanza.aview

import bohnanza.model.Game

class Tui {
  def processInputLine(input: String, game: Game): Game = {
    val splittedInput = input.split(" ").toList
    splittedInput(0) match {
      // case "start"
      case "draw" => {
        val playerIndex = splittedInput(1).toInt
        val player = game.players(playerIndex)
        val (updatedDeck, updatedPlayer) = player.drawCardFromDeck(game.deck)

        game.copy(
          players = game.players.updated(playerIndex, updatedPlayer),
          deck = updatedDeck
        )
      }
      // case "plant" => game.players[splittedInput[1]]
      // case "harvest"
      // case "take"
      // case "cards"
      // case _
    }
  }
}

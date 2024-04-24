package bohnanza.aview

import bohnanza.model.Game

class Tui {
  def processInputLine(input: String, game: Game): Game = {
    val splittedInput = input.split(" ").toList
    splittedInput(0) match {
      // case "start"

      // draw [playerIndex]
      // case "draw" => {
      //   val playerIndex = splittedInput(1).toInt
      //   game.playerDrawCardFromDeck(playerIndex)
      // }
      // case "plant" => game.players[splittedInput[1]]
      // harvest [playerIndex] [beanFieldIndex]
      case "harvest" => {
        val playerIndex = splittedInput(1).toInt
        val beanFieldIndex = splittedInput(2).toInt
        println(game.players(playerIndex).coins)
        println(game.players(playerIndex).beanFields)
        val updatedGame = game.playerHarvestField(playerIndex, beanFieldIndex)
        println(updatedGame.players(playerIndex).coins)
        println(
          updatedGame.players(playerIndex).beanFields(beanFieldIndex).bean
        )
        updatedGame
      }
      // case "take"
      // case "cards"
      // case _
    }
  }
}

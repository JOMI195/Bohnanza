package bohnanza.aview

import bohnanza.model.Game

class Tui {
  def processInputLine(input: String, game: Game): Game = {
    val splittedInput = input.split(" ").toList
    splittedInput(0) match {
      // case "start"

      // Usage: draw [playerIndex]
      case "draw" => {
        val playerIndex = splittedInput(1).toInt
        game.playerDrawCardFromDeck(playerIndex)
      }

      // Usage: plantFromHand [playerIndex] [beanFieldIndex]
      case "plant" => {
        val playerIndex = splittedInput(1).toInt
        val beanFieldIndex = splittedInput(2).toInt
        game.playerPlantCardFromHand(playerIndex, beanFieldIndex)
      }

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
      // turn
      // case "turn"
      // case "take"
      // case "cards"
      case "exit" => {
        println("Exiting game...")
        game.copy()
      }
      case _ => {
        println("Command not recognized. Type 'help' for commands.")
        game.copy()
      }
    }
  }
}

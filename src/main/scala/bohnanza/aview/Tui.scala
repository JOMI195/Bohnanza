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
      // draw cards to TurnOverField
      case "turn" => {
        println(game.turnOverField)
        val updatedGame = game.drawCardToTurnOverField()
        println(updatedGame.turnOverField)
        updatedGame
      }

      // take [playerIndex] [cardIndex] [beanFieldIndex]
      // take cards from TurnOverField
      case "take" => {
        val playerIndex = splittedInput(1).toInt
        val cardIndex = splittedInput(2).toInt
        val beanFieldIndex = splittedInput(3).toInt
        println(game.turnOverField)
        println(game.players(playerIndex))
        val updatedGame = game.playerPlantFromTurnOverField(
          playerIndex,
          cardIndex,
          beanFieldIndex
        )
        println(updatedGame.turnOverField)
        println(updatedGame.players(playerIndex))
        updatedGame
      }

      // case "cards"
      case "exit" => {
        println("Exiting game...")
        game
      }
      case _ => {
        println("Command not recognized. Type 'help' for commands.")
        game
      }
    }
  }
}

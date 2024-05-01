package bohnanza.controller

import bohnanza.model.*
import bohnanza.util.{Observable}

class Controller(var game: Game) extends Observable {
  def draw(playerIndex: Int): Unit = {
    game = game.playerDrawCardFromDeck(playerIndex = playerIndex)
    notifyObservers
  }

  def plant(playerIndex: Int, beanFieldIndex: Int): Unit = {
    game = game.playerPlantCardFromHand(playerIndex, beanFieldIndex)
    notifyObservers
  }

  def harvest(playerIndex: Int, beanFieldIndex: Int): Unit = {
    game = game.playerHarvestField(playerIndex, beanFieldIndex)
    notifyObservers
  }

  def turn: Unit = {
    game = game.drawCardToTurnOverField()
    notifyObservers
  }

  def take(playerIndex: Int, cardIndex: Int, beanFieldIndex: Int): Unit = {
    game = game.playerPlantFromTurnOverField(
      playerIndex,
      cardIndex,
      beanFieldIndex
    )
    notifyObservers
  }

}

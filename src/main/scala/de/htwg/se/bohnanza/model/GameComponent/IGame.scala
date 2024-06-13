package de.htwg.se.bohnanza.model.GameComponent

import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.{IPlayer}
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.{IDeck}
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.{
  ITurnOverField
}

trait IGame(
    val players: List[IPlayer],
    val currentPlayerIndex: Int,
    val deck: IDeck,
    val turnOverField: ITurnOverField
) {

  /* Converts the Game to a string representation */
  override def toString(): String

  /* Allows a player to draw a card from the deck and add it to their hand.
     Updates the player's hand and the game's deck. */
  def playerDrawCardFromDeck(playerIndex: Int): IGame

  /* Allows a player to plant a card from their hand into a specified bean field.
     Updates the player's state in the game. */
  def playerPlantCardFromHand(playerIndex: Int, beanFieldIndex: Int): IGame

  /* Allows a player to harvest a specified bean field, earning coins and resetting
     the field. Updates the player's state in the game. */
  def playerHarvestField(playerIndex: Int, beanFieldIndex: Int): IGame

  /* Allows a player to plant a bean from the turn over field into a specified
     bean field. Updates the turn over field and the player's state in the game. */
  def playerPlantFromTurnOverField(
      playerIndex: Int,
      cardIndex: Int,
      beanFieldIndex: Int
  ): IGame

  /* Draws two cards from the deck and adds them to the turn over field, updating
     the game's deck and turn over field. */
  def drawCardToTurnOverField(): IGame

  /* Creates a copy of the game */
  def copy(
      players: List[IPlayer] = this.players,
      currentPlayerIndex: Int = this.currentPlayerIndex,
      deck: IDeck = this.deck,
      turnOverField: ITurnOverField = this.turnOverField
  ): IGame
}

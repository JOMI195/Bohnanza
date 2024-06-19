package de.htwg.se.bohnanza.model.GameComponent

import de.htwg.se.bohnanza.model.GameComponent.{IGame}
import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.{IPlayer}
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.{IDeck}
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.{
  ITurnOverField
}
import de.htwg.se.bohnanza.model.GameComponent.Bean
import com.google.inject.Inject
import com.google.inject.name.Named

case class Game @Inject() (
    override val players: List[IPlayer],
    @Named("currentPlayerIndex") override val currentPlayerIndex: Int = -1,
    override val deck: IDeck,
    override val turnOverField: ITurnOverField
) extends IGame(players, currentPlayerIndex, deck, turnOverField) {

  override def toString(): String = {
    val turnOverField = "Turnoverfield: " + this.turnOverField + "\n"
    if (this.players.isEmpty) {
      return turnOverField + "\n" + "No players"
    }
    val players = this.players.map(_.toString).mkString("\n")
    turnOverField + "\n" + players
  }

  def playerDrawCardFromDeck(playerIndex: Int): IGame = {
    val player = players(playerIndex)
    val (card, updatedDeck) = deck.draw()
    val updatedHand = card match {
      case Some(card) => player.hand.addCard(card)
      case None       => player.hand
    }
    val updatedPlayer = player.copy(hand = updatedHand)
    copy(
      players = players.updated(playerIndex, updatedPlayer),
      deck = updatedDeck
    )
  }

  def playerPlantCardFromHand(playerIndex: Int, beanFieldIndex: Int): IGame = {
    val player = players(playerIndex)
    val updatedPlayer = players(playerIndex).plantCardFromHand(beanFieldIndex)
    val updatedPlayers = players.updated(playerIndex, updatedPlayer)
    copy(players = updatedPlayers)
  }

  def playerHarvestField(playerIndex: Int, beanFieldIndex: Int): IGame = {
    val updatedPlayer = players(playerIndex).harvestField(beanFieldIndex)
    val updatedPlayers = players.updated(playerIndex, updatedPlayer)
    copy(players = updatedPlayers)
  }

  def playerPlantFromTurnOverField(
      playerIndex: Int,
      cardIndex: Int,
      beanFieldIndex: Int
  ): IGame = {
    val (cardTaken, updatedTurnOverField) = turnOverField.takeCard(cardIndex)
    val updatedPlayer = players(playerIndex).plantToField(
      cardTaken,
      beanFieldIndex
    )
    copy(
      players = players.updated(playerIndex, updatedPlayer),
      turnOverField = updatedTurnOverField
    )
  }

  def drawCardToTurnOverField(): IGame = {
    val (firstCard, firstDeck) = deck.draw()
    val firstTurnOverField =
      turnOverField.addCardToTurnOverField(firstCard)

    val (secondCard, secondDeck) = firstDeck.draw()
    val secondTurnOverField =
      firstTurnOverField.addCardToTurnOverField(secondCard)
    copy(deck = secondDeck, turnOverField = secondTurnOverField)
  }

  def copy(
      players: List[IPlayer] = this.players,
      currentPlayerIndex: Int = this.currentPlayerIndex,
      deck: IDeck = this.deck,
      turnOverField: ITurnOverField = this.turnOverField
  ): IGame = Game(players, currentPlayerIndex, deck, turnOverField)
}

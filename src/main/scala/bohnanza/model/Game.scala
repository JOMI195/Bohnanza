package bohnanza.model

case class Game(
    players: List[Player],
    deck: Deck,
    turnOverField: TurnOverField
) {
  def copy(
      players: List[Player] = players,
      deck: Deck = deck,
      turnOverField: TurnOverField = turnOverField
  ): Game = Game(players, deck, turnOverField)

  /** Draws a card from the deck and adds it to the specified player's hand. If
    * the deck is empty, no card is drawn.
    */
  def playerDrawCardFromDeck(playerIndex: Int): Game = {
    println(s"Player ${playerIndex} attempting drawing card from deck...")
    val player = players(playerIndex)
    val (card, updatedDeck) = deck.draw()
    val updatedPlayer = card match {
      case Some(card) => player.addCardToHand(card)
      case None       => player
    }
    this.copy(
      players = this.players.updated(playerIndex, updatedPlayer),
      deck = updatedDeck
    )
  }

  def playerHarvestField(playerIndex: Int, beanFieldIndex: Int): Game = {
    val updatedPlayer = players(playerIndex).harvestField(beanFieldIndex)
    val updatedPlayers = players.updated(playerIndex, updatedPlayer)
    copy(players = updatedPlayers)
  }

  def drawCardToTurnOverField(): Game = {
    val (firstCard, firstDeck) = deck.draw()
    val firstTurnOverField =
      turnOverField.addCardToTurnOverField(firstCard)

    val (secondCard, secondDeck) = firstDeck.draw()
    val secondTurnOverField =
      firstTurnOverField.addCardToTurnOverField(secondCard)
    copy(deck = secondDeck, turnOverField = secondTurnOverField)
  }
}

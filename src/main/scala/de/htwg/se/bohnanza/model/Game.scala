package bohnanza.model

case class Game(
    players: List[Player],
    currentPlayerIndex: Int,
    deck: Deck,
    turnOverField: TurnOverField
) {

  override def toString(): String = {
    val turnOverField = "Turnoverfield: " + this.turnOverField + "\n"
    if (this.players.isEmpty) {
      return turnOverField + "\n" + "No players"
    }
    val players = this.players.map(_.toString).mkString("\n")
    turnOverField + "\n" + players
  }

  /** Draws a card from the deck and adds it to the specified player's hand. If
    * the deck is empty, no card is drawn.
    */
  def playerDrawCardFromDeck(playerIndex: Int): Game = {
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

  def playerPutCardToDeck(playerIndex: Int): Game = {
    val player = players(playerIndex)
    val (card, updatedHand) = player.hand.popLastCard()
    val updatedPlayer = player.copy(hand = updatedHand)
    val updatedDeck = deck.addToTop(card)
    copy(
      deck = updatedDeck,
      players = players.updated(playerIndex, updatedPlayer)
    )
  }

  /** Pops the first card from the hand cards of a player and plant it to the
    * players beanField
    */
  def playerPlantCardFromHand(playerIndex: Int, beanFieldIndex: Int): Game = {
    val player = players(playerIndex)
    val updatedPlayer = players(playerIndex).plantCardFromHand(beanFieldIndex)
    val updatedPlayers = players.updated(playerIndex, updatedPlayer)
    copy(players = updatedPlayers)
  }

  def playerHarvestField(playerIndex: Int, beanFieldIndex: Int): Game = {
    val updatedPlayer = players(playerIndex).harvestField(beanFieldIndex)
    val updatedPlayers = players.updated(playerIndex, updatedPlayer)
    copy(players = updatedPlayers)
  }

  def playerPlantFromTurnOverField(
      playerIndex: Int,
      cardIndex: Int,
      beanFieldIndex: Int
  ): Game = {
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

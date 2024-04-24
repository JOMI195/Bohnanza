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

  // def playerDrawCardFromDeck(playerIndex: Int): Game = {
  //   val player = players(playerIndex)
  //   val (card, updatedDeck) = deck.draw()
  //   val updatedPlayer = player.addCardToHand(card)
  //   this.copy(
  //     players = this.players.updated(playerIndex, updatedPlayer),
  //     deck = updatedDeck
  //   )
  // }

  def playerHarvestField(playerIndex: Int, beanFieldIndex: Int): Game = {
    val updatedPlayer = players(playerIndex).harvestField(beanFieldIndex)
    val updatedPlayers = players.updated(playerIndex, updatedPlayer)
    copy(players = updatedPlayers)
  }
}

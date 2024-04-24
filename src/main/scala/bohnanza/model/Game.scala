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

  def harvestField(playerIndex: Int, beanFieldIndex: Int) = {
    val updatedPlayer = players(playerIndex).harvestField(beanFieldIndex)
    val updatedPlayers = players.updated(playerIndex, updatedPlayer)
    copy(players = updatedPlayers)
  }
}

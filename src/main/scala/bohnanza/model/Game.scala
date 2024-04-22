package bohnanza.model

class Game(players: Vector[Player], deck: Deck, gameField: GameField) {
  def copy(
      players: Vector[Player] = players,
      deck: Deck = deck,
      gameField: GameField = gameField
  ): Game = Game(players, deck, gameField)

  def harvestField(playerIndex: Int, beanFieldIndex: Int) = {
    val updatedPlayer = players(playerIndex).harvestField(beanFieldIndex)
    val updatedPlayers = players.updated(playerIndex, updatedPlayer)
    copy(players = updatedPlayers)
  }
}

package bohnanza.model

class GameField(cards: Vector[Bean]) {
  def drawCardsFromDeck(deck: Deck): (GameField, Deck) {}
  def takeCard(cardIndex: Int): (GameField, Bean) {}
}

package bohnanza.model

class TurnOverField(cards: List[Bean]) {
  def turnOverCards(deck: Deck): (TurnOverField, Deck) {}
  def takeCard(cardIndex: Int): (TurnOverField, Bean) {}
}

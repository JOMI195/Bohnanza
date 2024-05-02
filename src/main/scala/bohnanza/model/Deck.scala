package bohnanza.model

case class Deck(cards: List[Bean]) {

  /** Draws a card from the deck. If the deck is empty, no card is drawn.
    */
  def draw(): (Option[Bean], Deck) = {
    val (card, updatedDeck) = cards match {
      case Nil          => (None, this)
      case head :: tail => (Some(head), Deck(tail))
    }
    (card, updatedDeck)
  }
}

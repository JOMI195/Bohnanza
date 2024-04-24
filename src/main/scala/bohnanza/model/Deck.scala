package bohnanza.model

case class Deck(cards: List[Bean]) {

  /** Draws a card from the deck. If the deck is empty, no card is drawn.
    */
  def draw(): (Option[Bean], Deck) = {
    println("Deck before draw:" + cards)
    val (card, updatedDeck) = cards match {
      case Nil          => (None, this)
      case head :: tail => (Some(head), Deck(tail))
    }
    println("Deck after draw:" + updatedDeck.cards)
    println("Drawn card:" + card)
    (card, updatedDeck)
  }
}

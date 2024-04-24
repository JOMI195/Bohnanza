package bohnanza.model

class Deck(cards: List[Bean]) {
  def draw(): (Option[Bean], Deck) = {
    cards match {
      case Nil          => (None, this)
      case head :: tail => (Some(head), Deck(tail))
    }
  }
}

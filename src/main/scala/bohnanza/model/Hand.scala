package bohnanza.model

case class Hand(cards: List[Bean]) {

  /** Adds a card to the hand cards
    */
  def addCard(card: Bean): Hand = {
    println("Hand before add" + cards)
    val updatedHand = copy(cards = cards :+ card)
    println("Hand after add" + updatedHand.cards)
    updatedHand
  }

  /** Pops the first card from the hand cards. If the hand cards is empty, no
    * card is returned.
    */
  def popCard(): (Option[Bean], Hand) = {
    println("hand before pop: " + cards)
    val (card, updatedCards) = cards match {
      case Nil          => (None, cards)
      case head :: tail => (Some(head), tail)
    }
    val updatedHand = copy(cards = updatedCards)
    println("Popped card: " + card)
    println("Hand after pop: " + updatedHand.cards)
    (card, updatedHand)
  }
}

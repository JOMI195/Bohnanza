package bohnanza.model

case class Hand(cards: List[Bean]) {

  override def toString(): String = {
    if (cards.isEmpty) {
      return "| empty |"
    }
    "| " + cards.map(_.toString).mkString(" | ") + " |"
  }

  /** Adds a card to the hand cards
    */
  def addCard(card: Bean): Hand = {
    val updatedHand = copy(cards = cards :+ card)
    updatedHand
  }

  /** Pops the first card from the hand cards. If the hand cards is empty, no
    * card is returned.
    */
  def popCard(): (Option[Bean], Hand) = {
    val (card, updatedCards) = cards match {
      case Nil          => (None, cards)
      case head :: tail => (Some(head), tail)
    }
    val updatedHand = copy(cards = updatedCards)
    (card, updatedHand)
  }

  def popLastCard(): (Option[Bean], Hand) = {
    cards match {
      case Nil => (None, this)
      case _   =>
        // returns all elements of the list except the last one.
        val updatedCards = cards.init
        val card = cards.last
        val updatedHand = copy(cards = updatedCards)
        (Some(card), updatedHand)
    }
  }
}

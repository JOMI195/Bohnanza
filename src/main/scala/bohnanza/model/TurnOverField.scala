package bohnanza.model

case class TurnOverField(cards: List[Bean]) {
  def addCardToTurnOverField(card: Option[Bean]): TurnOverField = {
    card match {
      case Some(card) => TurnOverField(cards :+ card)
      case None       => this
    }
  }

  def takeCard(cardIndex: Int): TurnOverField = TurnOverField(
    cards.patch(cardIndex, Nil, 1)
  )

}

package bohnanza.model

case class Player(
    name: String,
    beanFields: List[BeanField],
    coins: Int,
    cards: List[Bean]
) {
  def copy(
      name: String = name,
      beanFields: List[BeanField] = beanFields,
      coins: Int = coins,
      cards: List[Bean] = cards
  ): Player = Player(name, beanFields, coins, cards)

  def drawCardFromDeck(deck: Deck): (Deck, Player) = {
    val (card, updatedDeck) = deck.draw()
    val updatedPlayer = copy(cards = card.fold(cards)(_ :: cards))
    (updatedDeck, updatedPlayer)
  }

  /*   def buyBeanField(): Player {}
  def harvestField(beanFieldIndex: Int): Player {}
  def plantBeanFromCards(placeTwoCards: Boolean = false): Player {}
  def plantBeanFromGameField(
      gameField: TurnOverField,
      gameFieldIndex: Int,
      beanFieldIndex: Int
  ): (TurnOverField, Player) {} */
}

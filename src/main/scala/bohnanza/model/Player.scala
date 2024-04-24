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

  /** Adds a card to the hand cards
    */
  def addCardToHand(card: Bean): Player = {
    println("Player cards before add" + cards)
    val updatedPlayer = copy(cards = cards :+ card)
    println("Player cards after add" + updatedPlayer.cards)
    updatedPlayer
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

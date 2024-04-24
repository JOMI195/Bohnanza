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

  // def addCardToHand(card: Bean): Player = {
  //   this.copy(cards = cards :: card)
  // }

//   def buyBeanField(): Player {}
  def harvestField(beanFieldIndex: Int): Player = {
    val (coins, updatedBeanField) = beanFields(beanFieldIndex).harvestField()
    println(coins)
    copy(
      coins = coins,
      beanFields = beanFields.updated(beanFieldIndex, updatedBeanField)
    )
  }
//   def plantBeanFromCards(placeTwoCards: Boolean = false): Player {}
//   def plantBeanFromGameField(
//       gameField: TurnOverField,
//       gameFieldIndex: Int,
//       beanFieldIndex: Int
//   ): (TurnOverField, Player) {}
}

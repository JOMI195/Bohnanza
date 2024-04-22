package bohnanza.model

class Player(
    name: String,
    beanFields: Vector[BeanField],
    coins: Int,
    cards: Vector[Bean]
) {
  def copy(
      name: String = name,
      beanFields: Vector[BeanField] = beanFields,
      coins: Int = coins,
      cards: Vector[Bean] = cards
  ): Player = Player(name, beanFields, coins, cards)

  def buyBeanField(): Player {}
  def harvestField(beanFieldIndex: Int): Player {}
  def plantBeanFromCards(placeTwoCards: Boolean = false): Player {}
  def plantBeanFromGameField(
      gameField: GameField,
      gameFieldIndex: Int,
      beanFieldIndex: Int
  ): (GameField, Player) {}
}

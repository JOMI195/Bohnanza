package bohnanza.model

case class Player(
    name: String,
    beanFields: List[BeanField],
    coins: Int,
    cards: List[Bean]
) {

  /** Adds a card to the hand cards
    */
  def addCardToHand(card: Bean): Player = {
    println("Player cards before add" + cards)
    val updatedPlayer = copy(cards = cards :+ card)
    println("Player cards after add" + updatedPlayer.cards)
    updatedPlayer
  }

  /** Pops the first card from the hand cards. If the hand cards is empty, no
    * card is returned.
    */
  def popCardFromCards(): (Option[Bean], Player) = {
    println("Player cards before pop: " + cards)
    val (card, updatedCards) = cards match {
      case Nil          => (None, cards)
      case head :: tail => (Some(head), tail)
    }
    val updatedPlayer = copy(cards = updatedCards)
    println("Popped card: " + card)
    println("Player cards after pop: " + updatedPlayer.cards)
    (card, updatedPlayer)
  }

  /** Pops the first card from the hand cards and plant it to the beanField
    */
  def plantCardFromCards(beanFieldIndex: Int): Player = {
    val (card, updatedCardPlayer) = popCardFromCards()
    val updatedFieldPlayer = card match {
      case Some(card) => updatedCardPlayer.plantToField(card, beanFieldIndex)
      case None       => this
    }
    updatedFieldPlayer
  }

//   def buyBeanField(): Player {}
  def harvestField(beanFieldIndex: Int): Player = {
    val (coins, updatedBeanField) = beanFields(beanFieldIndex).harvestField()
    copy(
      coins = coins,
      beanFields = beanFields.updated(beanFieldIndex, updatedBeanField)
    )
  }

  def plantToField(bean: Bean, beanFieldIndex: Int): Player = {
    val updatedBeanField = beanFields(beanFieldIndex).plantToField(bean)
    val updatedBeanFields = beanFields.updated(beanFieldIndex, updatedBeanField)
    copy(beanFields = updatedBeanFields)
  }
}

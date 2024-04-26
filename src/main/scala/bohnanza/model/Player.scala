package bohnanza.model

case class Player(
    name: String,
    beanFields: List[BeanField],
    coins: Int,
    hand: Hand
) {

  /** Pops the first card from the hand cards and plant it to the beanField
    */
  def plantCardFromHand(beanFieldIndex: Int): Player = {
    val (card, updatedHand) = hand.popCard()
    val updatedPlayer = copy(hand = updatedHand)
    val updatedFieldPlayer = card match {
      case Some(card) => updatedPlayer.plantToField(card, beanFieldIndex)
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

package de.htwg.se.bohnanza.model.GameComponent.PlayerComponent

import de.htwg.se.bohnanza.model.GameComponent.HandComponent.{IHand, Hand}
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.{
  IBeanField,
  BeanField
}
import de.htwg.se.bohnanza.model.GameComponent.Bean
import com.google.inject.Inject

case class Player @Inject() (
    override val name: String,
    override val beanFields: List[IBeanField] = List(
      BeanField(None),
      BeanField(None),
      BeanField(None)
    ), // for now it has all three beanFields
    override val coins: Int = 0,
    override val hand: IHand = Hand(List.empty)
) extends IPlayer(name, beanFields, coins, hand) {

  override def toString: String = {
    val start = s"Player $name | coins: $coins |\n"
    val hand = "- Hand: " + this.hand + "\n"
    val beanFields =
      "- Beanfields: | " + this.beanFields
        .map(_.toString)
        .mkString(" | ") + " |\n"
    start + hand + beanFields
  }

  def plantCardFromHand(beanFieldIndex: Int): IPlayer = {
    val (card, updatedHand) = hand.popCard()
    val updatedPlayer = copy(hand = updatedHand)
    val updatedFieldPlayer = card match {
      case Some(card) => updatedPlayer.plantToField(card, beanFieldIndex)
      case None       => this
    }
    updatedFieldPlayer
  }

  def harvestField(beanFieldIndex: Int): IPlayer = {
    val (coins, updatedBeanField) = beanFields(beanFieldIndex).harvestField()
    copy(
      coins = coins,
      beanFields = beanFields.updated(beanFieldIndex, updatedBeanField)
    )
  }

  def plantToField(bean: Bean, beanFieldIndex: Int): IPlayer = {
    val updatedBeanField = beanFields(beanFieldIndex).plantToField(bean)
    val updatedBeanFields = beanFields.updated(beanFieldIndex, updatedBeanField)
    copy(beanFields = updatedBeanFields)
  }

  def copy(
      name: String = this.name,
      beanFields: List[IBeanField] = this.beanFields,
      coins: Int = this.coins,
      hand: IHand = this.hand
  ): IPlayer = Player(name, beanFields, coins, hand)
}

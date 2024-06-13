package de.htwg.se.bohnanza.model.GameComponent.PlayerComponent

import de.htwg.se.bohnanza.model.GameComponent.HandComponent.{IHand, Hand}
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.{
  IBeanField,
  BeanField
}
import de.htwg.se.bohnanza.model.GameComponent.Bean

case class Player(
    name: String,
    beanFields: List[BeanField] = List(
      BeanField(None),
      BeanField(None),
      BeanField(None)
    ), // for now it has all three beanFields
    coins: Int = 0,
    hand: Hand = Hand(List.empty)
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

  def plantCardFromHand(beanFieldIndex: Int): Player = {
    val (card, updatedHand) = hand.popCard()
    val updatedPlayer = copy(hand = updatedHand)
    val updatedFieldPlayer = card match {
      case Some(card) => updatedPlayer.plantToField(card, beanFieldIndex)
      case None       => this
    }
    updatedFieldPlayer
  }

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

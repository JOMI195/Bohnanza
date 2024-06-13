package de.htwg.se.bohnanza.model.GameComponent.PlayerComponent

import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.model.GameComponent.HandComponent.{IHand}
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.{IBeanField}

trait IPlayer(
    val name: String,
    val beanFields: List[IBeanField],
    val coins: Int,
    val hand: IHand
) {

  /* Converts the Player to a string representation. */
  override def toString: String

  /* Plants a card from the player's hand into a specified bean field. If the hand
     is not empty, it removes the top card from the hand and plants it in the specified
     bean field. */
  def plantCardFromHand(beanFieldIndex: Int): IPlayer

  /* Harvests a specified bean field, adding the earned coins to the player's total
     and resetting the harvested bean field. */
  def harvestField(beanFieldIndex: Int): IPlayer

  /* Plants a specified bean into a specified bean field, updating the bean field
     accordingly. */
  def plantToField(bean: Bean, beanFieldIndex: Int): IPlayer
}

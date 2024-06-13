package de.htwg.se.bohnanza.model.GameComponent.HandComponent

import de.htwg.se.bohnanza.model.GameComponent.Bean

trait IHand(val cards: List[Bean]) {

  /* Converts the Hand to a string representation */
  override def toString(): String

  /* Adds a card to the hand and returns an updated IHand instance. */
  def addCard(card: Bean): IHand

  /* Removes the top card from the hand and returns it along with the updated
     IHand instance. If the hand is empty, returns (None, this). */
  def popCard(): (Option[Bean], IHand)

  /* Creates a copy of the hand */
  def copy(cards: List[Bean] = this.cards): IHand
}

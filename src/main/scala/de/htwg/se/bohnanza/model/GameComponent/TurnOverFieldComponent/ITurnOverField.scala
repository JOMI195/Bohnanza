package de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent

import de.htwg.se.bohnanza.model.GameComponent.Bean

trait ITurnOverField(cards: List[Bean]) {

  /* Converts the TurnOverField to a string representation. */
  override def toString(): String

  /* Adds a card to the TurnOverField. If the provided card is Some(card), it
     appends the card to the list of cards. If the provided card is None, it
     returns the current TurnOverField unchanged. */
  def addCardToTurnOverField(card: Option[Bean]): ITurnOverField

  /* Removes a card from the TurnOverField at the specified index and returns a
     tuple containing the removed card and the updated TurnOverField. */
  def takeCard(cardIndex: Int): (Bean, ITurnOverField)
}

package de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent

import de.htwg.se.bohnanza.model.GameComponent.Bean

case class TurnOverField(cards: List[Bean]) extends ITurnOverField(cards) {

  override def toString(): String = {
    if (cards.isEmpty) {
      return "| empty |"
    }
    "| " + cards.map(_.toString).mkString(" | ") + " |"
  }

  def addCardToTurnOverField(card: Option[Bean]): TurnOverField = {
    card match {
      case Some(card) => TurnOverField(cards :+ card)
      case None       => this
    }
  }

  def takeCard(cardIndex: Int): (Bean, TurnOverField) = {
    val cardTaken = cards(cardIndex)
    val updatedTurnOverField = cards.patch(cardIndex, Nil, 1)
    (cardTaken, TurnOverField(updatedTurnOverField))
  }
}

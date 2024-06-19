package de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent

import de.htwg.se.bohnanza.model.GameComponent.Bean
import com.google.inject.Inject

case class TurnOverField @Inject() (override val cards: List[Bean])
    extends ITurnOverField(cards) {

  override def toString(): String = {
    if (cards.isEmpty) {
      return "| empty |"
    }
    "| " + cards.map(_.toString).mkString(" | ") + " |"
  }

  def addCardToTurnOverField(card: Option[Bean]): ITurnOverField = {
    card match {
      case Some(card) => TurnOverField(cards :+ card)
      case None       => this
    }
  }

  def takeCard(cardIndex: Int): (Bean, ITurnOverField) = {
    val cardTaken = cards(cardIndex)
    val updatedTurnOverField = cards.patch(cardIndex, Nil, 1)
    (cardTaken, TurnOverField(updatedTurnOverField))
  }

  def copy(cards: List[Bean] = this.cards): ITurnOverField = TurnOverField(
    cards
  )
}

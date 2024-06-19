package de.htwg.se.bohnanza.model.GameComponent.HandComponent

import de.htwg.se.bohnanza.model.GameComponent.Bean
import com.google.inject.Inject

case class Hand @Inject() (override val cards: List[Bean])
    extends IHand(cards) {

  override def toString(): String = {
    if (cards.isEmpty) {
      return "| empty |"
    }
    "| " + cards.map(_.toString).mkString(" | ") + " |"
  }

  def addCard(card: Bean): IHand = {
    val updatedHand = copy(cards = cards :+ card)
    updatedHand
  }

  def popCard(): (Option[Bean], IHand) = {
    val (card, updatedCards) = cards match {
      case Nil          => (None, cards)
      case head :: tail => (Some(head), tail)
    }
    val updatedHand = copy(cards = updatedCards)
    (card, updatedHand)
  }

  def copy(cards: List[Bean] = this.cards): IHand = Hand(cards)
}

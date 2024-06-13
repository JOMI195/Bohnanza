package de.htwg.se.bohnanza.model.GameComponent.HandComponent

import de.htwg.se.bohnanza.model.GameComponent.Bean

case class Hand(cards: List[Bean]) extends IHand(cards) {

  override def toString(): String = {
    if (cards.isEmpty) {
      return "| empty |"
    }
    "| " + cards.map(_.toString).mkString(" | ") + " |"
  }

  def addCard(card: Bean): Hand = {
    val updatedHand = copy(cards = cards :+ card)
    updatedHand
  }

  def popCard(): (Option[Bean], Hand) = {
    val (card, updatedCards) = cards match {
      case Nil          => (None, cards)
      case head :: tail => (Some(head), tail)
    }
    val updatedHand = copy(cards = updatedCards)
    (card, updatedHand)
  }
}

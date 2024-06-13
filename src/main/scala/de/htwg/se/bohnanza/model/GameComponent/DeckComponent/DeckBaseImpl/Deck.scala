package de.htwg.se.bohnanza.model.GameComponent.DeckComponent

import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.{IDeck}

case class Deck(override val cards: List[Bean]) extends IDeck(cards) {

  def draw(): (Option[Bean], IDeck) = {
    val (card, updatedDeck) = cards match {
      case Nil          => (None, this)
      case head :: tail => (Some(head), Deck(tail))
    }
    (card, updatedDeck)
  }

  def copy(cards: List[Bean] = this.cards): IDeck = Deck(cards)
}

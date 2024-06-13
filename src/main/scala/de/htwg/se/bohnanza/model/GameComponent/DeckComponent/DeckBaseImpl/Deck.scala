package de.htwg.se.bohnanza.model.GameComponent.DeckComponent

import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.{IDeck}

case class Deck(cards: List[Bean]) extends IDeck(cards) {

  def draw(): (Option[Bean], Deck) = {
    val (card, updatedDeck) = cards match {
      case Nil          => (None, this)
      case head :: tail => (Some(head), Deck(tail))
    }
    (card, updatedDeck)
  }
}

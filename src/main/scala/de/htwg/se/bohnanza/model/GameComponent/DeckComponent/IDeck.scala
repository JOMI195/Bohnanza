package de.htwg.se.bohnanza.model.GameComponent.DeckComponent

import scala.util.Random
import de.htwg.se.bohnanza.model.GameComponent.Bean

trait IDeck(cards: List[Bean]) {

  /* Draws a card from the deck. If the deck is not empty, it returns the top card
     as Some(card) along with the updated Deck instance with the card removed.
     If the deck is empty, it returns (None, this) indicating no card was drawn. */
  def draw(): (Option[Bean], IDeck)
}

import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*

class DeckSpec extends AnyWordSpec with Matchers {

  "A Deck" should {
    "draw from a non-empty deck" in {
      val initialCards = List(Bean.Firebean, Bean.Firebean, Bean.Firebean)
      val initialDeck = Deck(initialCards)

      val (drawnCard, updatedDeck) = initialDeck.draw()

      drawnCard should matchPattern { case Some(Bean.Firebean) => }
      updatedDeck should be(Deck(List(Bean.Firebean, Bean.Firebean)))
    }

    "not draw from an empty deck" in {
      val initialDeck = Deck(Nil)

      val (drawnCard, updatedDeck) = initialDeck.draw()

      drawnCard should be(None)
      updatedDeck should be(Deck(Nil))
    }
  }
}

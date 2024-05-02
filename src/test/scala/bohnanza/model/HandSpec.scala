import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*

class HandSpec extends AnyWordSpec with Matchers {

  "A Hand" should {
    "add a card correctly" in {
      val initialHand = Hand(List.empty)
      val cardToAdd = Bean.Firebean

      val updatedHand = initialHand.addCard(cardToAdd)

      updatedHand.cards should be(List(cardToAdd))
    }

    "pop a card correctly" when {
      "the hand is not empty" in {
        val initialHand = Hand(List(Bean.Firebean, Bean.Firebean))
        val (poppedCard, updatedHand) = initialHand.popCard()

        poppedCard should be(Some(Bean.Firebean))
        updatedHand.cards should be(List(Bean.Firebean))
      }

      "the hand is empty" in {
        val initialHand = Hand(List.empty)
        val (poppedCard, updatedHand) = initialHand.popCard()

        poppedCard should be(None)
        updatedHand.cards should be(List.empty)
      }
    }
  }
}

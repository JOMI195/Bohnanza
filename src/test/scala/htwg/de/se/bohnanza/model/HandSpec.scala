import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*

class HandSpec extends AnyWordSpec with Matchers {

  "A Hand" should {

    "have correct string representation" when {
      "the hand is empty" in {
        val emptyHand = Hand(List.empty)
        emptyHand.toString should be("| empty |")
      }

      "the hand contains cards" in {
        val handWithCards = Hand(List(Bean.ChiliBean, Bean.BlueBean))
        handWithCards.toString should be("| ChiliBean | BlueBean |")
      }
    }

    "add a card correctly" in {
      val initialHand = Hand(List.empty)
      val cardToAdd = Bean.ChiliBean

      val updatedHand = initialHand.addCard(cardToAdd)

      updatedHand.cards should be(List(cardToAdd))
    }

    "pop a card correctly" when {
      "the hand is not empty" in {
        val initialHand = Hand(List(Bean.ChiliBean, Bean.ChiliBean))
        val (poppedCard, updatedHand) = initialHand.popCard()

        poppedCard should be(Some(Bean.ChiliBean))
        updatedHand.cards should be(List(Bean.ChiliBean))
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

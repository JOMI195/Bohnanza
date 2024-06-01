import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*

val emptyTurnOverField = TurnOverField(List.empty)
val initialTurnOverField = TurnOverField(List(Bean.ChiliBean, Bean.ChiliBean))

class TurnOverFieldSpec extends AnyWordSpec with Matchers {

  "have correct string representation" when {
    "the field is empty" in {
      emptyTurnOverField.toString should be("| empty |")
    }

    "the field contains cards" in {
      initialTurnOverField.toString should be("| ChiliBean | ChiliBean |")
    }
  }

  "add a card to the turnOverField" when {
    "card is None" in {
      val updatedTurnOverField = emptyTurnOverField.addCardToTurnOverField(None)
      emptyTurnOverField shouldBe updatedTurnOverField
    }

    "card is valid" in {
      val updatedTurnOverField =
        emptyTurnOverField.addCardToTurnOverField(Some(Bean.ChiliBean))
      updatedTurnOverField.cards.size shouldBe emptyTurnOverField.cards.size + 1
    }
  }

  "take a card from the turnOverField" in {
    val (cardTaken, updatedTurnOverField) = initialTurnOverField.takeCard(0)
    cardTaken shouldBe Bean.ChiliBean
    updatedTurnOverField.cards.size shouldBe initialTurnOverField.cards.size - 1
  }
}

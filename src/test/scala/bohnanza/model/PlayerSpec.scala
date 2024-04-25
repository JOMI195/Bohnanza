import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*

class PlayerSpec extends AnyWordSpec with Matchers {
  "Player" should {

    "create a copy with updated attributes" in {
      val originalPlayer = Player("Original", List.empty, 0, List.empty)

      val updatedPlayer = originalPlayer.copy(
        name = "Updated",
        coins = 10,
        beanFields = List(BeanField(None)),
        cards = List(Bean.Firebean)
      )

      updatedPlayer.name should be("Updated")
      updatedPlayer.coins should be(10)
      updatedPlayer.cards should be(List(Bean.Firebean))
      updatedPlayer.beanFields should be(List(BeanField(None)))
    }

    "add a card to hand" in {
      val player = Player("TestPlayer", List.empty, 0, List.empty)
      val cardToAdd = Bean.Firebean

      val updatedPlayer = player.addCardToHand(cardToAdd)

      updatedPlayer.cards should be(List(cardToAdd))
    }

    "pop a card from cards" when {
      "cards list is not empty" in {
        val initialCards = List(Bean.Firebean, Bean.Firebean, Bean.Firebean)
        val player = Player("TestPlayer", List.empty, 0, initialCards)

        val (poppedCard, updatedPlayer) = player.popCardFromCards()

        poppedCard should be(Some(Bean.Firebean))
        updatedPlayer.cards should be(List(Bean.Firebean, Bean.Firebean))
      }

      "cards list is empty" in {
        val player = Player("TestPlayer", List.empty, 0, List.empty)

        val (poppedCard, updatedPlayer) = player.popCardFromCards()

        poppedCard should be(None)
        updatedPlayer.cards should be(List.empty)
      }
    }

    "plant a card from cards" when {
      "cards list is not empty" in {
        val initialCards = List(Bean.Firebean, Bean.Firebean, Bean.Firebean)
        val initialPlayer =
          Player("TestPlayer", List(BeanField(None)), 0, initialCards)

        val updatedPlayer = initialPlayer.plantCardFromCards(0)

        updatedPlayer.beanFields(0).bean should be(Some(Bean.Firebean))
        updatedPlayer.cards should be(List(Bean.Firebean, Bean.Firebean))
      }

      "cards list is empty" in {
        val player = Player("TestPlayer", List.empty, 0, List.empty)

        val updatedPlayer = player.plantCardFromCards(0)

        updatedPlayer.beanFields should be(List.empty)
        updatedPlayer.cards should be(List.empty)
      }
    }
  }
}

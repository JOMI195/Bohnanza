import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*

class PlayerSpec extends AnyWordSpec with Matchers {
  "Player" should {

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

    "harvest beanField at a specific index" in {
      val initialCards = List.empty
      val initialPlayer =
        Player(
          "TestPlayer",
          List(BeanField(Option(Bean.Firebean), 4)),
          0,
          initialCards
        )

      val updatedPlayer = initialPlayer.harvestField(0)
      updatedPlayer.beanFields.head.quantity shouldBe 0
      updatedPlayer.coins shouldBe 2
    }

    "plant a bean to a specific beanField" in {
      val initialCards = List.empty
      val initialPlayer =
        Player(
          "TestPlayer",
          List(BeanField(Option(Bean.Firebean), 3)),
          0,
          initialCards
        )

      val updatedPlayer = initialPlayer.plantToField(Bean.Firebean, 0)
      updatedPlayer.beanFields.head.quantity shouldBe 4
    }
  }
}

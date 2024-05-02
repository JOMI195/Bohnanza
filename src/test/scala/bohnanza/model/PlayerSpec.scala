import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*

class PlayerSpec extends AnyWordSpec with Matchers {
  "Player" should {
    "cards list is not empty" in {
      val initialCards = List(Bean.Firebean, Bean.Firebean, Bean.Firebean)
      val player = Player("TestPlayer", List.empty, 0, Hand(initialCards))

      val (poppedCard, updatedHand) = player.hand.popCard()
      val updatedPlayer = player.copy(hand = updatedHand)

      poppedCard should be(Some(Bean.Firebean))
      updatedPlayer.hand.cards should be(List(Bean.Firebean, Bean.Firebean))
    }

    "cards list is empty" in {
      val player = Player("TestPlayer", List.empty, 0, Hand(List.empty))

      val (poppedCard, updatedHand) = player.hand.popCard()
      val updatedPlayer = player.copy(hand = updatedHand)

      poppedCard should be(None)
      updatedPlayer.hand.cards should be(List.empty)
    }

    "plant a card from cards" when {
      "cards list is not empty" in {
        val initialCards = List(Bean.Firebean, Bean.Firebean, Bean.Firebean)
        val initialPlayer =
          Player("TestPlayer", List(BeanField(None)), 0, Hand(initialCards))

        val updatedPlayer = initialPlayer.plantCardFromHand(0)

        updatedPlayer.beanFields(0).bean should be(Some(Bean.Firebean))
        updatedPlayer.hand.cards should be(List(Bean.Firebean, Bean.Firebean))
      }

      "cards list is empty" in {
        val player = Player("TestPlayer", List.empty, 0, Hand(List.empty))

        val updatedPlayer = player.plantCardFromHand(0)

        updatedPlayer.beanFields should be(List.empty)
        updatedPlayer.hand.cards should be(List.empty)
      }
    }

    "harvest beanField at a specific index" in {
      val initialCards = List.empty
      val initialPlayer =
        Player(
          "TestPlayer",
          List(BeanField(Option(Bean.Firebean), 4)),
          0,
          Hand(initialCards)
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
          Hand(initialCards)
        )

      val updatedPlayer = initialPlayer.plantToField(Bean.Firebean, 0)
      updatedPlayer.beanFields.head.quantity shouldBe 4
    }
  }
}

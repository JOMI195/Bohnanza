import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.Player
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.BeanField
import de.htwg.se.bohnanza.model.GameComponent.HandComponent.Hand
import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.*
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.TurnOverField

class PlayerSpec extends AnyWordSpec with Matchers {
  "Player" should {

    "have correct string representation" when {
      "hand and beanFields are empty" in {
        val player =
          Player("TestPlayer", List(BeanField(None)), 0, Hand(List.empty))
        val expectedString =
          "Player TestPlayer | coins: 0 |\n- Hand: | empty |\n- Beanfields: | empty |\n"
        player.toString should be(expectedString)
      }

      "hand is not empty and beanFields are empty" in {
        val initialCards = List(Bean.ChiliBean, Bean.BlueBean)
        val player =
          Player("TestPlayer", List(BeanField(None)), 0, Hand(initialCards))
        val expectedString =
          "Player TestPlayer | coins: 0 |\n- Hand: | ChiliBean | BlueBean |\n- Beanfields: | empty |\n"
        player.toString should be(expectedString)
      }

      "hand is empty and beanFields are not empty" in {
        val player = Player(
          "TestPlayer",
          List(BeanField(Option(Bean.ChiliBean), 3)),
          0,
          Hand(List.empty)
        )
        val expectedString =
          "Player TestPlayer | coins: 0 |\n- Hand: | empty |\n- Beanfields: | ChiliBean x3 |\n"
        player.toString should be(expectedString)
      }

      "hand and beanFields are not empty" in {
        val initialCards = List(Bean.ChiliBean, Bean.BlueBean)
        val player = Player(
          "TestPlayer",
          List(BeanField(Option(Bean.ChiliBean), 3)),
          0,
          Hand(initialCards)
        )
        val expectedString =
          "Player TestPlayer | coins: 0 |\n- Hand: | ChiliBean | BlueBean |\n- Beanfields: | ChiliBean x3 |\n"
        player.toString should be(expectedString)
      }
    }

    "cards list is not empty" in {
      val initialCards = List(Bean.ChiliBean, Bean.ChiliBean, Bean.ChiliBean)
      val player = Player("TestPlayer", List.empty, 0, Hand(initialCards))

      val (poppedCard, updatedHand) = player.hand.popCard()
      val updatedPlayer = player.copy(hand = updatedHand)

      poppedCard should be(Some(Bean.ChiliBean))
      updatedPlayer.hand.cards should be(List(Bean.ChiliBean, Bean.ChiliBean))
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
        val initialCards = List(Bean.ChiliBean, Bean.ChiliBean, Bean.ChiliBean)
        val initialPlayer =
          Player("TestPlayer", List(BeanField(None)), 0, Hand(initialCards))

        val updatedPlayer = initialPlayer.plantCardFromHand(0)

        updatedPlayer.beanFields(0).bean should be(Some(Bean.ChiliBean))
        updatedPlayer.hand.cards should be(List(Bean.ChiliBean, Bean.ChiliBean))
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
          List(BeanField(Option(Bean.ChiliBean), 4)),
          0,
          Hand(initialCards)
        )

      val updatedPlayer = initialPlayer.harvestField(0)
      updatedPlayer.beanFields.head.quantity shouldBe 0
      updatedPlayer.coins shouldBe 1
    }

    "plant a bean to a specific beanField" in {
      val initialCards = List.empty
      val initialPlayer =
        Player(
          "TestPlayer",
          List(BeanField(Option(Bean.ChiliBean), 3)),
          0,
          Hand(initialCards)
        )

      val updatedPlayer = initialPlayer.plantToField(Bean.ChiliBean, 0)
      updatedPlayer.beanFields.head.quantity shouldBe 4
    }
  }
}

import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*

class GameSpec extends AnyWordSpec with Matchers {
  "Game" should {

    "have correct string representation" when {
      "there are no players, deck, and turnOverField" in {
        val game =
          Game(List.empty, 0, Deck(List.empty), TurnOverField(List.empty))
        val expectedString = "Turnoverfield: | empty |\n\nNo players"
        game.toString should be(expectedString)
      }

      "there are players, deck, and turnOverField" in {
        val initialPlayer =
          Player("Player1", List(BeanField(None)), 0, Hand(List.empty))
        val initialPlayers = List(initialPlayer)
        val initialDeck = Deck(List(Bean.Firebean))
        val initialTurnOverField = TurnOverField(List(Bean.BlueBean))
        val game = Game(initialPlayers, 0, initialDeck, initialTurnOverField)
        val expectedString =
          "Turnoverfield: | BlueBean |\n\nPlayer Player1 | coins: 0 |\n- Hand: | empty |\n- Beanfields: | empty |\n"
        game.toString should be(expectedString)
      }
    }

    "draw a card from deck and add it to the player's hand" when {
      val initialPlayer = Player("Player1", List.empty, 0, Hand(List.empty))
      val initialPlayers = List(initialPlayer)
      val initialTurnOverField = TurnOverField(List.empty)

      "card drawn from deck is valid" in {
        val initialDeck = Deck(List(Bean.Firebean, Bean.Firebean))
        val game = Game(initialPlayers, 0, initialDeck, initialTurnOverField)
        val updatedGame = game.playerDrawCardFromDeck(0)

        updatedGame.deck.cards shouldBe List(Bean.Firebean)
        updatedGame.players.head.hand.cards should be(List(Bean.Firebean))
      }

      "card drawn from deck is none" in {
        val initialDeck = Deck(List.empty)
        val game = Game(initialPlayers, 0, initialDeck, initialTurnOverField)
        val updatedGame = game.playerDrawCardFromDeck(0)
        updatedGame shouldBe game
      }

    }

    "plant a card from hand to the player's bean field" in {
      val initialPlayer =
        Player("Player1", List(BeanField(None)), 0, Hand(List(Bean.Firebean)))
      val initialPlayers = List(initialPlayer)
      val initialDeck = Deck(List.empty)
      val initialTurnOverField = TurnOverField(List.empty)
      val game = Game(initialPlayers, 0, initialDeck, initialTurnOverField)

      val updatedGame = game.playerPlantCardFromHand(0, 0)

      updatedGame.players.head.hand.cards should be(List.empty)
      updatedGame.players.head.beanFields.head.bean should be(
        Some(Bean.Firebean)
      )
    }

    "harvest a field of a specific player" in {
      val initialPlayer =
        Player(
          "Player1",
          List(BeanField(Option(Bean.Firebean), 4)),
          0,
          Hand(List(Bean.Firebean))
        )
      val initialPlayers = List(initialPlayer)
      val initialDeck = Deck(List.empty)
      val initialTurnOverField = TurnOverField(List.empty)
      val game = Game(initialPlayers, 0, initialDeck, initialTurnOverField)

      val updatedGame = game.playerHarvestField(0, 0)

      updatedGame.players.head.coins shouldBe 2
      updatedGame.players.head.beanFields.head.quantity shouldBe 0
    }

    "plant a card taken from the turnOverField" in {
      val initialPlayer =
        Player(
          "Player1",
          List(BeanField(Option(Bean.Firebean), 3)),
          0,
          Hand(List(Bean.Firebean))
        )
      val initialPlayers = List(initialPlayer)
      val initialDeck = Deck(List.empty)
      val initialTurnOverField =
        TurnOverField(List(Bean.Firebean, Bean.Firebean))
      val game = Game(initialPlayers, 0, initialDeck, initialTurnOverField)
      val updatedGame = game.playerPlantFromTurnOverField(0, 0, 0)

      updatedGame.players.head.beanFields.head.quantity shouldBe 4
    }

    "draw card from deck to turnOverField" in {
      val initialDeck = Deck(List(Bean.Firebean, Bean.Firebean))
      val game = Game(List.empty, 0, initialDeck, TurnOverField(List.empty))
      val updatedGame = game.drawCardToTurnOverField()

      updatedGame.turnOverField.cards.size shouldBe 2
      updatedGame.turnOverField.cards shouldBe List(
        Bean.Firebean,
        Bean.Firebean
      )
    }
  }
}

import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*

class GameSpec extends AnyWordSpec with Matchers {
  "Game" should {

    "create a copy with updated attributes" in {
      val initialPlayers = List(Player("Player1", List.empty, 0, List.empty))
      val initialDeck = Deck(List.empty)
      val initialTurnOverField = TurnOverField(List.empty)
      val game = Game(initialPlayers, initialDeck, initialTurnOverField)

      val updatedPlayers = List(Player("Player2", List.empty, 10, List.empty))
      val updatedDeck = Deck(List(Bean.Firebean))
      val updatedTurnOverField = TurnOverField(List(Bean.Firebean))
      val updatedGame = game.copy(
        players = updatedPlayers,
        deck = updatedDeck,
        turnOverField = updatedTurnOverField
      )

      updatedGame.players shouldBe updatedPlayers
      updatedGame.deck shouldBe updatedDeck
      updatedGame.turnOverField shouldBe updatedTurnOverField
    }

    "draw a card from deck and add it to the player's hand" in {
      val initialPlayer = Player("Player1", List.empty, 0, List.empty)
      val initialPlayers = List(initialPlayer)
      val initialDeck = Deck(List(Bean.Firebean, Bean.Firebean))
      val initialTurnOverField = TurnOverField(List.empty)
      val game = Game(initialPlayers, initialDeck, initialTurnOverField)

      val updatedGame = game.playerDrawCardFromDeck(0)

      updatedGame.deck.cards shouldBe List(Bean.Firebean)
      updatedGame.players.head.cards shouldBe List(Bean.Firebean)
    }

    "plant a card from hand to the player's bean field" in {
      val initialPlayer =
        Player("Player1", List(BeanField(None)), 0, List(Bean.Firebean))
      val initialPlayers = List(initialPlayer)
      val initialDeck = Deck(List.empty)
      val initialTurnOverField = TurnOverField(List.empty)
      val game = Game(initialPlayers, initialDeck, initialTurnOverField)

      val updatedGame = game.playerPlantCardFromHand(0, 0)

      updatedGame.players.head.cards shouldBe List.empty
      updatedGame.players.head.beanFields.head.bean shouldBe Some(
        Bean.Firebean
      )
    }
  }
}

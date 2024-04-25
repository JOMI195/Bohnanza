import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*
import bohnanza.aview.*

class TuiSpec extends AnyWordSpec with Matchers {
  "Tui" should {
    "process 'draw' command correctly" in {
      val initialPlayer = Player("Player1", List.empty, 0, List.empty)
      val initialPlayers = List(initialPlayer)
      val initialDeck = Deck(List(Bean.Firebean))
      val initialTurnOverField = TurnOverField(List.empty)
      val initialGame = Game(initialPlayers, initialDeck, initialTurnOverField)
      val tui = new Tui()

      val updatedGame = tui.processInputLine("draw 0", initialGame)

      updatedGame.deck.cards shouldBe List.empty
      updatedGame.players.head.cards shouldBe List(Bean.Firebean)
    }

    "process 'plant' command correctly" in {
      val initialPlayer =
        Player("Player1", List(BeanField(None)), 0, List(Bean.Firebean))
      val initialPlayers = List(initialPlayer)
      val initialDeck = Deck(List.empty)
      val initialTurnOverField = TurnOverField(List.empty)
      val initialGame = Game(initialPlayers, initialDeck, initialTurnOverField)
      val tui = new Tui()

      val updatedGame = tui.processInputLine("plant 0 0", initialGame)

      updatedGame.players.head.cards shouldBe List.empty
      updatedGame.players.head.beanFields.head.bean shouldBe Some(
        Bean.Firebean
      )
    }

    "process 'exit' command correctly" in {
      val initialPlayer = Player("Player1", List.empty, 0, List.empty)
      val initialPlayers = List(initialPlayer)
      val initialDeck = Deck(List.empty)
      val initialTurnOverField = TurnOverField(List.empty)
      val initialGame = Game(initialPlayers, initialDeck, initialTurnOverField)
      val tui = new Tui()

      val updatedGame = tui.processInputLine("exit", initialGame)

      updatedGame shouldBe initialGame
    }

    "print error message for unrecognized command" in {
      val initialPlayer = Player("Player1", List.empty, 0, List.empty)
      val initialPlayers = List(initialPlayer)
      val initialDeck = Deck(List.empty)
      val initialTurnOverField = TurnOverField(List.empty)
      val initialGame = Game(initialPlayers, initialDeck, initialTurnOverField)
      val tui = new Tui()

      val updatedGame = tui.processInputLine("invalid_command", initialGame)

      updatedGame shouldBe initialGame
    }
  }
}

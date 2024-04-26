import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*
import bohnanza.aview.*

class TuiSpec extends AnyWordSpec with Matchers {
  val tui = new Tui()

  val initialPlayer = Player("Player1", List.empty, 0, Hand(List.empty))
  val initialPlayers = List(initialPlayer)
  val initialDeck = Deck(List(Bean.Firebean, Bean.Firebean))
  val emptyTurnOverField = TurnOverField(List.empty)
  val initialGame = Game(initialPlayers, initialDeck, emptyTurnOverField)

  val emptyDeck = Deck(List.empty)
  val emptyGame = Game(initialPlayers, emptyDeck, emptyTurnOverField)

  "Tui" should {
    "process 'draw' command correctly" in {
      val updatedGame = tui.processInputLine("draw 0", initialGame)
      updatedGame.deck.cards shouldBe List(Bean.Firebean)
      updatedGame.players.head.hand.cards shouldBe List(Bean.Firebean)
    }

    "process 'plant' command correctly" in {
      val initialPlayer =
        Player("Player1", List(BeanField(None)), 0, Hand(List(Bean.Firebean)))
      val game = Game(List(initialPlayer), emptyDeck, emptyTurnOverField)
      val updatedGame = tui.processInputLine("plant 0 0", game)
      updatedGame.players.head.hand.cards shouldBe List.empty
      updatedGame.players.head.beanFields.head.bean should be(
        Some(Bean.Firebean)
      )
    }

    "process 'exit' command correctly" in {
      val updatedGame = tui.processInputLine("exit", emptyGame)
      updatedGame shouldBe emptyGame
    }

    "print error message for unrecognized command" in {
      val updatedGame = tui.processInputLine("invalid_command", emptyGame)
      updatedGame shouldBe emptyGame
    }
  }

  "process 'turn' command correctly" in {
    val updatedGame = tui.processInputLine("turn", initialGame)
    updatedGame.deck.cards.size shouldBe initialGame.deck.cards.size - 2
    updatedGame.turnOverField.cards.size shouldBe initialGame.turnOverField.cards.size + 2
  }

  "process 'harvest' command correctly" in {
    val player =
      Player(
        "Player",
        List(BeanField(Option(Bean.Firebean), 4)),
        0,
        Hand(List.empty)
      )
    val game = Game(List(player), emptyDeck, emptyTurnOverField)
    val updatedGame = tui.processInputLine("harvest 0 0", game)
    updatedGame.players.head.beanFields shouldBe List(BeanField(None, 0))
    updatedGame.players.head.coins shouldBe game.players.head.coins + 2
  }

  "process 'take' command correctly" in {
    val initialPlayer =
      Player("Player1", List(BeanField(None)), 0, Hand(List.empty))
    val game = Game(
      List(initialPlayer),
      emptyDeck,
      TurnOverField(List(Bean.Firebean, Bean.Firebean))
    )
    val updatedGame = tui.processInputLine("take 0 0 0", game)
    updatedGame.turnOverField.cards.size shouldBe game.turnOverField.cards.size - 1
    updatedGame.players.head.beanFields.head.quantity shouldBe game.players.head.beanFields.head.quantity + 1
  }
}

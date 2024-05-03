import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*
import bohnanza.controller.*
import bohnanza.aview.*

import bohnanza.aview.Tui
import bohnanza.controller.*

class TuiSpec extends AnyWordSpec with Matchers {

  val initialPlayer =
    Player("Player1", List(BeanField(None)), 0, Hand(List.empty))
  val initialPlayers = List(initialPlayer)
  val initialDeck = Deck(List(Bean.Firebean, Bean.Firebean))
  val emptyTurnOverField = TurnOverField(List.empty)
  val initialGame = Game(initialPlayers, initialDeck, emptyTurnOverField)

  val controller = Controller(initialGame)

  val tui = new Tui(controller)

  val emptyDeck = Deck(List.empty)
  val emptyGame = Game(initialPlayers, emptyDeck, emptyTurnOverField)

  val errorMessage = "Invalid Input\n"

  "The Tui class" should {

    "draw" when {
      val msg = "Usage: draw [playerIndex]"
      "process draw command with valid player index" in {
        val input = "draw 0"
        tui.processInputLine(input) shouldBe None
      }

      "process draw command with invalid player index format" in {
        val input = "draw invalid"
        tui.processInputLine(input) shouldBe Some(errorMessage + msg)
      }

      "process draw command with missing player index" in {
        val input = "draw"
        tui.processInputLine(input) shouldBe Some(msg)
      }
    }

    "plant" when {
      val msg = "Usage: plant [playerIndex] [beanFieldIndex]"
      "process plant command with valid player and bean field indexes" in {
        val initialPlayer =
          Player("Player1", List(BeanField(None)), 0, Hand(List(Bean.Firebean)))
        val game = Game(List(initialPlayer), emptyDeck, emptyTurnOverField)
        val controller = Controller(game)
        val tui = Tui(controller)
        val input = "plant 0 0"
        tui.processInputLine(input) shouldBe None
      }

      "process plant command with invalid input format" in {
        val input = "plant invalid"
        tui.processInputLine(input) shouldBe Some(errorMessage + msg)
      }

      "process plant command with missing player or bean field index" in {
        val input = "plant 0"
        tui.processInputLine(input) shouldBe Some(msg)
      }
    }

    "turn" when {
      val msg = "Usage: turn"
      "process turn command" in {
        val controller = Controller(initialGame)
        val tui = new Tui(controller)
        val input = "turn"
        tui.processInputLine(input) shouldBe None
      }

      "process turn command with invalid input" in {
        val input = "turn invalid"
        tui.processInputLine(input) shouldBe Some(errorMessage + msg)
      }

      "process turn command with unneccessary indexes" in {
        val input = "turn 0 0 0"
        tui.processInputLine(input) shouldBe Some(msg)
      }
    }

    "harvest" when {
      val msg = "Usage: harvest [playerIndex] [beanFieldIndex]"
      "process harvest command with missing indexes" in {
        val input = "harvest"
        tui.processInputLine(input) shouldBe Some(msg)
      }

      "process harvest command with invalid input" in {
        val input = "harvest invalid"
        tui.processInputLine(input) shouldBe Some(errorMessage + msg)
      }

      "process harvest command" in {
        val input = "harvest 0 0"
        tui.processInputLine(input) shouldBe None
      }
    }

    "take" when {
      val turnOverField = TurnOverField(List(Bean.Firebean, Bean.Firebean))
      val game = Game(initialPlayers, initialDeck, turnOverField)
      val controller = Controller(game)
      val tui = Tui(controller)
      val msg = "Usage: take [playerIndex] [cardIndex] [beanFieldIndex]"
      "process take command with valid player, card, and bean field indexes" in {
        val input = "take 0 0 0"
        tui.processInputLine(input) shouldBe None
      }

      "process take command with invalid input format" in {
        val input = "take invalid"
        tui.processInputLine(input) shouldBe Some(errorMessage + msg)
      }

      "process take command with missing indexes" in {
        val input = "take 0"
        tui.processInputLine(input) shouldBe Some(msg)
      }
    }

    "process exit command" in {
      val input = "exit"
      tui.processInputLine(input) shouldBe Some("Exiting game...")
    }

    "process unrecognized command" in {
      val input = "invalid command"
      tui.processInputLine(input) shouldBe Some(
        "Command not recognized. Type 'help' for commands."
      )
    }
  }
}

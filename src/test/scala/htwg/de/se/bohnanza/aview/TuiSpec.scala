import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.bohnanza.aview.Tui
import de.htwg.se.bohnanza.controller.ControllerComponent.Controller
import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.Player
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.BeanField
import de.htwg.se.bohnanza.model.GameComponent.HandComponent.Hand
import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.Deck
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.TurnOverField
import de.htwg.se.bohnanza.model.GameComponent.Game
import de.htwg.se.bohnanza.model.ArgsHandlerComponent.HandlerResponse
import de.htwg.se.bohnanza.model.PhaseStateComponent.*
import de.htwg.se.bohnanza.util.ObserverEvent

class TuiSpec extends AnyWordSpec with Matchers {

  val initialPlayer =
    Player("Player1", List(BeanField(None)), 0, Hand(List.empty))
  val initialPlayers = List(initialPlayer)
  val initialDeck = Deck(List(Bean.ChiliBean, Bean.ChiliBean))
  val emptyTurnOverField = TurnOverField(List.empty)
  val initialGame = Game(initialPlayers, 0, initialDeck, emptyTurnOverField)

  val controller = Controller(initialGame)

  val tui = new Tui(controller)

  val emptyDeck = Deck(List.empty)
  val emptyGame = Game(initialPlayers, 0, emptyDeck, emptyTurnOverField)

  val errorMessage = "Invalid Input\n"

  "The Tui class" should {
    "createPlayer" when {
      val msg = "Usage: createPlayer [playerName]"

      "process createPlayer command with name" in {
        val input = "createPlayer Jomi"
        tui.processInputLine(input) shouldBe None
      }

      "process createPlayer command with no name" in {
        val input = "createPlayer"
        tui.processInputLine(input) shouldBe Some("Invalid Input" + "\n" + msg)
      }
    }

    "undo" when {
      val msg = "Usage: undo"

      "process undo command with no arguments" in {
        val input = "undo"
        tui.processInputLine(input) shouldBe None
      }

      "process undo command with arguments" in {
        val input = "undo 1"
        tui.processInputLine(input) shouldBe Some(msg)
      }

      "process undo command with invalid arguments" in {
        val input = "undo invalid"
        tui.processInputLine(input) shouldBe Some(errorMessage + msg)
      }
    }

    "redo" when {
      val msg = "Usage: redo"

      "process redo command with no arguments" in {
        val input = "redo"
        tui.processInputLine(input) shouldBe None
      }

      "process redo command with arguments" in {
        val input = "redo 1"
        tui.processInputLine(input) shouldBe Some(msg)
      }

      "process redo command with invalid arguments" in {
        val input = "redo invalid"
        tui.processInputLine(input) shouldBe Some(errorMessage + msg)
      }
    }

    "next" when {
      val msg = "Usage: next"

      "process next command with no arguments" in {
        val input = "next"
        tui.processInputLine(input) shouldBe None
      }

      "process next command with arguments" in {
        val input = "next 1"
        tui.processInputLine(input) shouldBe Some(msg)
      }

      "process next command with invalid arguments" in {
        val input = "next invalid"
        tui.processInputLine(input) shouldBe Some(errorMessage + msg)
      }
    }

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
          Player(
            "Player1",
            List(BeanField(None)),
            0,
            Hand(List(Bean.ChiliBean))
          )
        val game = Game(List(initialPlayer), 0, emptyDeck, emptyTurnOverField)
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
      val turnOverField = TurnOverField(List(Bean.ChiliBean, Bean.ChiliBean))
      val game = Game(initialPlayers, 0, initialDeck, turnOverField)
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

    "update correctly if error in controller occures" when {
      "handles BeanFieldIndexError" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.update(HandlerResponse.BeanFieldIndexError)
        }
        stream.toString().trim() shouldBe "You don't have this bean field."
      }

      "handles PlayerIndexError" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.update(HandlerResponse.PlayerIndexError)
        }
        stream.toString().trim() shouldBe "This player does not exist."
      }

      "handles CurrentPlayerIndexError" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.update(HandlerResponse.CurrentPlayerIndexError)
        }
        stream
          .toString()
          .trim() shouldBe "This player is not the current player."
      }

      "handles TurnOverFieldIndexError" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.update(HandlerResponse.TurnOverFieldIndexError)
        }
        stream
          .toString()
          .trim() shouldBe "You can't access a turn-over card with this index."
      }

      "handles HandIndexError" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.update(HandlerResponse.HandIndexError)
        }
        stream
          .toString()
          .trim() shouldBe "You don't have any cards to plant anymore."
      }

      "handles MethodError" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.update(HandlerResponse.MethodError)
        }
        stream
          .toString()
          .trim() shouldBe "You can't use this method in this phase."
      }

      "handles ArgsError" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.update(HandlerResponse.ArgsError)
        }
        stream
          .toString()
          .trim() shouldBe "Debug: Argument error in controller and handler."
      }

      "handles MissingPlayerCreationError" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.update(HandlerResponse.MissingPlayerCreationError)
        }
        stream
          .toString()
          .trim() shouldBe "You can't go to the next phase because you didn't create any player yet."
      }

      "handles TakeInvalidPlantError" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.update(HandlerResponse.TakeInvalidPlantError)
        }
        stream
          .toString()
          .trim() shouldBe "The bean from the turn over field does not match with the bean on your bean field."
      }

      "handles InvalidPlantError" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.update(HandlerResponse.InvalidPlantError)
        }
        stream
          .toString()
          .trim() shouldBe "The bean from your hand does not match with the bean on your bean field."
      }

      "handles Success" in {
        // This case should not print anything, so we check if the stream is empty.
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.update(HandlerResponse.Success)
        }
        stream
          .toString()
          .trim() shouldBe "Debug: Success should not be printed."
      }

    }

    "update correctly if action happened successfully" when {
      val initialPlayer =
        Player(
          "Player1",
          List(BeanField(Option(Bean.ChiliBean), 4)),
          0,
          Hand(List(Bean.ChiliBean))
        )
      val initialPlayers = List(initialPlayer)
      val initialDeck = Deck(List(Bean.ChiliBean, Bean.ChiliBean))
      val emptyTurnOverField = TurnOverField(List.empty)
      val initialGame = Game(initialPlayers, 0, initialDeck, emptyTurnOverField)

      val controller = Controller(initialGame)
      val tui = new Tui(controller)

      val currentPlayer =
        controller.game.players(controller.game.currentPlayerIndex)

      "handle PhaseChange event" in {
        val playCardStream = new java.io.ByteArrayOutputStream()
        controller.phase = PlayCardPhase()
        Console.withOut(playCardStream) {
          controller.notifyObservers(ObserverEvent.PhaseChange)
        }
        playCardStream
          .toString()
          .trim()
          .replaceAll("\r\n", "\n") shouldBe (
          "Phase: PlayCardPhase\n" +
            "The allowed methods are:\n" +
            " - harvest\n" +
            " - plant\n" +
            " - draw\n" +
            " - turn\n"
        ).trim()

        val tradeAndPlantStream = new java.io.ByteArrayOutputStream()
        controller.phase = TradeAndPlantPhase()
        Console.withOut(tradeAndPlantStream) {
          controller.notifyObservers(ObserverEvent.PhaseChange)
        }
        tradeAndPlantStream
          .toString()
          .trim()
          .replaceAll(
            "\r\n",
            "\n"
          ) shouldBe (
          "Phase: TradeAndPlantPhase\n" +
            "The allowed methods are:\n" +
            " - harvest\n" +
            " - plant\n"
        ).trim()

        val drawCardPhaseStream = new java.io.ByteArrayOutputStream()
        controller.phase = DrawCardsPhase()
        Console.withOut(drawCardPhaseStream) {
          controller.notifyObservers(ObserverEvent.PhaseChange)
        }
        drawCardPhaseStream
          .toString()
          .trim()
          .replaceAll(
            "\r\n",
            "\n"
          ) shouldBe (
          "No method except next is allowed here, because everything is done automatically for you. :)\n"
        ).trim()

        controller.phase = PlayCardPhase()
      }

      "handle Harvest event" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          controller.notifyObservers(ObserverEvent.Harvest)
        }
        stream
          .toString()
          .trim()
          .replaceAll(
            "\r\n",
            "\n"
          ) shouldBe currentPlayer.toString.trim()
      }

      "handle Take event" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          controller.notifyObservers(ObserverEvent.Take)
        }
        stream
          .toString()
          .trim()
          .replaceAll(
            "\r\n",
            "\n"
          ) shouldBe currentPlayer.toString.trim()
      }

      "handle Plant event" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          controller.notifyObservers(ObserverEvent.Plant)
        }
        stream
          .toString()
          .trim()
          .replaceAll(
            "\r\n",
            "\n"
          ) shouldBe currentPlayer.toString.trim()
      }

      "handle GameInfo event" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          controller.notifyObservers(ObserverEvent.GameInfo)
        }
        stream
          .toString()
          .trim()
          .replaceAll(
            "\r\n",
            "\n"
          ) shouldBe controller.game.toString.trim()
      }

      "handle Draw event" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          controller.notifyObservers(ObserverEvent.Draw)
        }
        stream
          .toString()
          .trim()
          .replaceAll(
            "\r\n",
            "\n"
          ) shouldBe currentPlayer.toString.trim()
      }

      "handle Turn event" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          controller.notifyObservers(ObserverEvent.Turn)
        }
        stream
          .toString()
          .trim()
          .replaceAll(
            "\r\n",
            "\n"
          ) shouldBe controller.game.turnOverField.toString
          .trim()
      }

      "handle CreatePlayer event" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          controller.notifyObservers(ObserverEvent.CreatePlayer)
        }
        stream
          .toString()
          .trim()
          .replaceAll(
            "\r\n",
            "\n"
          ) shouldBe controller.game.toString
          .trim()
      }
    }
  }
}

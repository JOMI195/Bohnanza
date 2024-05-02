import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*
import bohnanza.controller.*
import bohnanza.aview.*

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

  "Tui" should {
    "process 'draw' command correctly" when {
      val msg = "Usage: draw [playerIndex] "
      "user inputs invalid arguments: non-integer" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.processInputLine("draw not an integer")
        }
        val desiredOutput = errorMessage + msg
        stream.toString.trim() shouldBe desiredOutput.trim()
      }

      "user inputs invalid arguments: incorrect args count" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.processInputLine("draw 0 0 0")
        }
        val desiredOutput = msg
        stream.toString.trim() shouldBe desiredOutput.trim()
      }

      "user uses the command correctly" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.processInputLine("draw 0")
        }
        val desiredOutput =
          "Turnoverfield: | empty |\n" + "\n" +
            "Player Player1 | coins: 0 |\n" +
            "- Hand: | Firebean |\n" +
            "- Beanfields: | empty |"
        stream.toString.trim() shouldBe desiredOutput.trim()
      }
    }

    "process 'plant' command correctly" when {
      val msg = "Usage: plant [playerIndex] [beanFieldIndex]"
      val initialPlayer =
        Player("Player1", List(BeanField(None)), 0, Hand(List(Bean.Firebean)))
      val game = Game(List(initialPlayer), emptyDeck, emptyTurnOverField)
      val controller = Controller(game)
      val tui = Tui(controller)

      "user inputs valid arguments" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.processInputLine("plant 0 0")
        }
        val desiredOutput =
          "Turnoverfield: | empty |\n" +
            "\n" +
            "Player Player1 | coins: 0 |\n" +
            "- Hand: | empty |\n" +
            "- Beanfields: | Firebean x1 |"

        stream.toString.trim() shouldBe desiredOutput.trim()
      }

      "user inputs invalid arguments: non-integer" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.processInputLine("plant notAnInteger 0")
        }
        val desiredOutput = "Invalid Input\n" + msg
        stream.toString.trim() shouldBe desiredOutput.trim()
      }

      "user inputs invalid arguments: incorrect args count" in {
        val stream = new java.io.ByteArrayOutputStream()
        Console.withOut(stream) {
          tui.processInputLine("plant 0 0 0 0")
        }
        val desiredOutput = msg
        stream.toString.trim() shouldBe desiredOutput.trim()
      }
    }

    "process 'exit' command correctly" in {
      val stream = new java.io.ByteArrayOutputStream()
      Console.withOut(stream) {
        tui.processInputLine("exit")
      }
      stream.toString().trim() shouldBe "Exiting game..."
    }

    "print error message for unrecognized command" in {
      val stream = new java.io.ByteArrayOutputStream()
      Console.withOut(stream) {
        tui.processInputLine("invalid_command")
      }
      stream
        .toString()
        .trim() shouldBe "Command not recognized. Type 'help' for commands."
    }
  }

  "process 'turn' command correctly" in {
    val controller = Controller(initialGame)
    val tui = new Tui(controller)
    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      tui.processInputLine("turn")
    }
    val desiredOutput =
      "Turnoverfield: | Firebean | Firebean |\n" + "\n" + "Player Player1 | coins: 0 |\n" + "- Hand: | empty |\n" + "- Beanfields: | empty |"
    stream.toString().trim() shouldBe desiredOutput.trim()
  }

  "process 'harvest' command correctly" when {
    val msg = "Usage: harvest [playerIndex] [beanFieldIndex]"
    val initialPlayer = Player(
      "Player1",
      List(BeanField(Some(Bean.Firebean), 4)),
      0,
      Hand(List.empty)
    )
    val game = Game(List(initialPlayer), emptyDeck, emptyTurnOverField)
    val controller = Controller(game)
    val tui = Tui(controller)

    "user inputs valid arguments" in {
      val stream = new java.io.ByteArrayOutputStream()
      Console.withOut(stream) {
        tui.processInputLine("harvest 0 0")
      }
      val desiredOutput = "Turnoverfield: | empty |\n" +
        "\n" +
        "Player Player1 | coins: 2 |\n" +
        "- Hand: | empty |\n" +
        "- Beanfields: | empty |"
      stream.toString.trim() shouldBe desiredOutput.trim()
    }

    "user inputs invalid arguments: non-integer" in {
      val stream = new java.io.ByteArrayOutputStream()
      Console.withOut(stream) {
        tui.processInputLine("harvest notAnInteger 0")
      }

      val desiredOutput = errorMessage + msg
      stream.toString.trim() shouldBe desiredOutput.trim()
    }

    "user inputs invalid arguments: incorrect args count" in {
      val stream = new java.io.ByteArrayOutputStream()
      Console.withOut(stream) {
        tui.processInputLine("harvest 0 0 0 0 0")
      }
      val desiredOutput = msg
      stream.toString.trim() shouldBe desiredOutput.trim()
    }
  }

  "process 'take' command correctly" when {
    val msg = "Usage: take [playerIndex] [cardIndex] [beanFieldIndex]"
    val initialPlayer =
      Player("Player1", List(BeanField(None)), 0, Hand(List.empty))
    val game = Game(
      List(initialPlayer),
      emptyDeck,
      TurnOverField(List(Bean.Firebean, Bean.Firebean))
    )
    val controller = Controller(game)
    val tui = Tui(controller)

    "user inputs valid arguments" in {
      val stream = new java.io.ByteArrayOutputStream()
      Console.withOut(stream) {
        tui.processInputLine("take 0 0 0")
      }
      val desiredOutput =
        "Turnoverfield: | Firebean |\n" +
          "\n" +
          "Player Player1 | coins: 0 |\n" +
          "- Hand: | empty |\n" +
          "- Beanfields: | Firebean x1 |"

      stream.toString.trim() shouldBe desiredOutput.trim()
    }

    "user inputs invalid arguments: non-integer" in {
      val stream = new java.io.ByteArrayOutputStream()
      Console.withOut(stream) {
        tui.processInputLine("take notAnInteger")
      }
      val desiredOutput = errorMessage + msg
      stream.toString.trim() shouldBe desiredOutput.trim()
    }

    "user inputs invalid arguments: incorrect args count" in {
      val stream = new java.io.ByteArrayOutputStream()
      Console.withOut(stream) {
        tui.processInputLine("take 0 0")
      }

      val desiredOutput = msg
      stream.toString.trim() shouldBe desiredOutput.trim()
    }
  }
}

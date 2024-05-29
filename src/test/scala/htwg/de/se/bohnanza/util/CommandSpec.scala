import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import bohnanza.controller.Controller
import bohnanza.model.*
import bohnanza.util.Command
import bohnanza.util.UndoManager

class TestCommand(controller: Controller) extends Command(controller) {
  override def doStep: Unit = {}
}

class UndoManagerSpec extends AnyWordSpec with Matchers {
  val initialPlayer =
    Player("Player1", List(BeanField(None)), 0, Hand(List.empty))
  val initialPlayers = List(initialPlayer)
  val initialDeck = Deck(List(Bean.Firebean, Bean.Firebean))
  val emptyTurnOverField = TurnOverField(List.empty)
  val initialGame = Game(initialPlayers, 0, initialDeck, emptyTurnOverField)

  val controller = Controller(initialGame)

  "The Tui class" should {

    "performing doStep" should {
      "add the command to the undo stack and execute the command" in {
        val undoManager = new UndoManager()
        val command = new TestCommand(controller)

        undoManager.doStep(command)

        undoManager.undoStack.headOption shouldBe Some(command)
      }
    }

    "performing undoStep" should {
      "undo the last command and move it to the redo stack" in {
        val undoManager = new UndoManager()
        val command = new TestCommand(controller)

        undoManager.doStep(command)
        undoManager.undoStack.headOption shouldBe Some(command)

        undoManager.undoStep

        undoManager.undoStack shouldBe empty
        undoManager.redoStack.headOption shouldBe Some(command)
      }
    }

    "performing redoStep" should {
      "redo the last undone command and move it back to the undo stack" in {
        val undoManager = new UndoManager()
        val command = new TestCommand(controller)

        undoManager.doStep(command)
        undoManager.undoStep
        undoManager.undoStack shouldBe empty
        undoManager.redoStack.headOption shouldBe Some(command)

        undoManager.redoStep

        undoManager.redoStack shouldBe empty
        undoManager.undoStack.headOption shouldBe Some(command)
      }
    }
  }
}

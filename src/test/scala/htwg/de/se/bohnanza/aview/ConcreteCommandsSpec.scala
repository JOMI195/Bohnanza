import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import bohnanza.controller._
import bohnanza.model._

class ConcreteCommandsSpec extends AnyWordSpec with Matchers {

  val initialPlayer =
    Player("Player1", List(BeanField(None)), 0, Hand(List.empty))
  val initialPlayers = List(initialPlayer)
  val initialDeck = Deck(List(Bean.Firebean, Bean.Firebean))
  val emptyTurnOverField = TurnOverField(List.empty)
  val initialGame = Game(initialPlayers, 0, initialDeck, emptyTurnOverField)

  val controller = Controller(initialGame)

  val emptyDeck = Deck(List.empty)
  val emptyGame = Game(initialPlayers, 0, emptyDeck, emptyTurnOverField)

  "ConcreteCommands" should {
    "PlayerCreationCommand" should {
      "executing doStep" should {
        "add a new player to the game" in {
          val controller =
            new Controller(emptyGame)
          val playerName = "Alice"
          val command = PlayerCreationCommand(controller, playerName)

          command.doStep

          val game = controller.game
          game.players should have size 2
          game.players.last.name shouldEqual playerName
        }
      }
    }

    "DrawCommand" should {
      "executing doStep" should {
        "draw a card for the specified player" in {
          val playerIndex = 0
          val command = DrawCommand(controller, playerIndex)

          command.doStep

          val updatedGame = controller.game
          updatedGame.players(playerIndex).hand.cards should not be empty
        }
      }
    }

    "PlantCommand" should {
      "executing doStep" should {
        "plant a card from hand to the specified bean field" in {
          val initialPlayer =
            Player(
              "Player1",
              List(BeanField(None)),
              0,
              Hand(List(Bean.Firebean))
            )
          val initialPlayers = List(initialPlayer)
          val initialDeck = Deck(List(Bean.Firebean, Bean.Firebean))
          val emptyTurnOverField = TurnOverField(List.empty)
          val initialGame =
            Game(initialPlayers, 0, initialDeck, emptyTurnOverField)

          val controller = Controller(initialGame)
          val playerIndex = 0
          val beanFieldIndex = 0
          val command = PlantCommand(controller, playerIndex, beanFieldIndex)

          command.doStep

          val updatedGame = controller.game
          updatedGame.players(playerIndex).hand.cards shouldBe empty
          updatedGame
            .players(playerIndex)
            .beanFields(beanFieldIndex)
            .bean should contain(
            Bean.Firebean
          )
        }
      }
    }

    "NextPhaseCommand" should {
      "executing doStep" should {
        "move the game to the next phase" in {
          val command = NextPhaseCommand(controller)

          command.doStep

          controller.phase shouldBe a[PlayCardPhase]
        }
      }
    }

    "HarvestCommand" should {
      "executing doStep" should {
        "harvest the specified bean field of the specified player" in {
          val playerIndex = 0
          val beanFieldIndex = 0
          val command = HarvestCommand(controller, playerIndex, beanFieldIndex)

          command.doStep

          val updatedGame = controller.game
          updatedGame
            .players(playerIndex)
            .beanFields(beanFieldIndex)
            .bean shouldBe None
        }
      }
    }

    "TurnCommand" should {
      "executing doStep" should {
        "draw two cards to the turn-over field" in {
          val command = TurnCommand(controller)

          command.doStep

          val updatedGame = controller.game
          updatedGame.turnOverField.cards should have size 1
        }
      }
    }

    "TakeCommand" should {
      "executing doStep" should {
        "plant a card from the turn-over field to the specified bean field of the specified player" in {
          val initialPlayer =
            Player("Player1", List(BeanField(None)), 0, Hand(List.empty))
          val initialPlayers = List(initialPlayer)
          val initialDeck = Deck(List(Bean.Firebean, Bean.Firebean))
          val emptyTurnOverField = TurnOverField(List(Bean.Firebean))
          val initialGame =
            Game(initialPlayers, 0, initialDeck, emptyTurnOverField)

          val controller = Controller(initialGame)

          val playerIndex = 0
          val cardIndex = 0
          val beanFieldIndex = 0
          val command =
            TakeCommand(controller, playerIndex, cardIndex, beanFieldIndex)

          command.doStep

          val updatedGame = controller.game
          updatedGame.turnOverField.cards shouldBe empty
          updatedGame
            .players(playerIndex)
            .beanFields(beanFieldIndex)
            .bean contains (
            Bean.Firebean
          )
        }
      }
    }
  }
}

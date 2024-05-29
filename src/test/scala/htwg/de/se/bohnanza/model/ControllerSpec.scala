import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*
import bohnanza.controller.*

val initialPlayer =
  Player(
    "Player1",
    List(BeanField(Option(Bean.Firebean), 4)),
    0,
    Hand(List(Bean.Firebean))
  )
val initialPlayers = List(initialPlayer)
val initialDeck = Deck(List(Bean.Firebean, Bean.Firebean))
val test = TurnOverField(List.empty)
val initialGame = Game(initialPlayers, 0, initialDeck, test)

class ControllerSpec extends AnyWordSpec with Matchers {
  "when changing phase, correctly changes state if players are full" in {
    val controller = Controller(initialGame)
    controller.nextPhase
    // since we start with the GameInitializationPhase
    controller.phase.isInstanceOf[PlayCardPhase] shouldBe true
    controller.game.currentPlayerIndex shouldBe 0
  }

  "error when notifying also calls notify method" when {
    // should lead to a PlayerIndexError
    "draw" in {
      val controller = Controller(initialGame)
      controller.nextPhase
      val gameBeforeDraw = controller.game
      controller.draw(1)
      controller.game shouldBe gameBeforeDraw
    }

    "plant" in {
      val controller = Controller(initialGame)
      val gameBeforePlant = controller.game
      controller.plant(1, 0)
      controller.game shouldBe gameBeforePlant
    }

    "harvest" in {
      val controller = Controller(initialGame)
      val gameBeforeHarvest = controller.game
      controller.harvest(1, 0)
      controller.game shouldBe gameBeforeHarvest
    }

    "turn" in {
      val controller = Controller(initialGame)
      controller.phase = TradeAndPlantPhase()
      val gameBeforeTurn = controller.game
      controller.turn
      controller.game shouldBe gameBeforeTurn
    }

    "take" in {
      val controller = Controller(initialGame)
      val gameBeforeTake = controller.game
      controller.take(1, 0, 0)
      controller.game shouldBe gameBeforeTake
    }

  }
}

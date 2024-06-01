import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*
import bohnanza.controller.*

val initialPlayer =
  Player(
    "Player1",
    List(BeanField(Option(Bean.ChiliBean), 4)),
    0,
    Hand(List(Bean.ChiliBean))
  )
val initialPlayers = List(initialPlayer)
val initialDeck = Deck(List(Bean.ChiliBean, Bean.ChiliBean))
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

  "draw" in {
    val controller = Controller(initialGame, phase = PlayCardPhase())
    val cardsBeforeLength = controller.game.players(0).hand.cards.length
    controller.draw(0)
    controller.game.players(0).hand.cards.length shouldBe cardsBeforeLength + 1
  }

  "plant" in {
    val controller = Controller(initialGame, phase = PlayCardPhase())
    val quantityBeforePlant = controller.game.players(0).beanFields(0).quantity
    controller.plant(0, 0)
    controller.game
      .players(0)
      .beanFields(0)
      .quantity shouldBe quantityBeforePlant + 1
  }

  "harvest" in {
    val controller = Controller(initialGame, phase = PlayCardPhase())
    val coinsBeforeHarvest = controller.game.players(0).coins
    controller.harvest(0, 0)
    controller.game
      .players(0)
      .coins shouldBe coinsBeforeHarvest + 2
    controller.game.players(0).beanFields(0).quantity shouldBe 0
  }

  "turn" in {
    val controller = Controller(initialGame, phase = PlayCardPhase())
    controller.turn
    controller.game.turnOverField.cards.length shouldBe 2
  }

  "take" in {
    val initialPlayers = List(initialPlayer)
    val initialDeck = Deck(List(Bean.ChiliBean, Bean.ChiliBean))
    val test = TurnOverField(List(Bean.ChiliBean, Bean.ChiliBean))
    val initialGame = Game(initialPlayers, 0, initialDeck, test)
    val controller = Controller(initialGame, phase = TradeAndPlantPhase())
    val quantityBeforePlant = controller.game.players(0).beanFields(0).quantity
    controller.take(0, 0, 0)
    controller.game
      .players(0)
      .beanFields(0)
      .quantity shouldBe quantityBeforePlant + 1
  }

  "error when notifying also calls notify method" when {
    // should lead to a PlayerIndexError
    "draw" in {
      val controller = Controller(initialGame, phase = PlayCardPhase())
      val gameBeforeDraw = controller.game
      // wrong index!
      controller.draw(1)
      controller.game shouldBe gameBeforeDraw
    }

    "next" in {
      val initialPlayers = List.empty
      val initialDeck = Deck(List(Bean.ChiliBean, Bean.ChiliBean))
      val test = TurnOverField(List(Bean.ChiliBean, Bean.ChiliBean))
      val initialGame = Game(initialPlayers, 0, initialDeck, test)
      val controller = Controller(initialGame)
      controller.nextPhase
      controller.phase.isInstanceOf[GameInitializationPhase] shouldBe true
    }

    "createPlayer" in {
      val initialPlayers = List.empty
      val initialDeck = Deck(List(Bean.ChiliBean, Bean.ChiliBean))
      val test = TurnOverField(List.empty)
      val initialGame = Game(initialPlayers, 0, initialDeck, test)
      val controller = Controller(initialGame, phase = PlayCardPhase())
      val gameBeforeNext = controller.game
      controller.createPlayer("Jomi")
      controller.game shouldBe gameBeforeNext
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

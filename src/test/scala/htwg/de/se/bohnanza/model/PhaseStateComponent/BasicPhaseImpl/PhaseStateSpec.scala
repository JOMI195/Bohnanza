import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.Player
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.BeanField
import de.htwg.se.bohnanza.model.GameComponent.HandComponent.Hand
import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.*
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.TurnOverField
import de.htwg.se.bohnanza.model.GameComponent.*
import de.htwg.se.bohnanza.model.PhaseStateComponent.*

class PhaseStateSpec extends AnyWordSpec with Matchers {
  val p1 = Player(
    name = "Player1",
    coins = 0,
    beanFields = List(BeanField(None)),
    hand = Hand(List.empty)
  )
  val p2 = Player(
    name = "Player2",
    coins = 0,
    beanFields = List(BeanField(None)),
    hand = Hand(List.empty)
  )
  val d = FullDeckCreateStrategy().createDeck()
  val t = TurnOverField(cards = List.empty)

  val initialGame = Game(
    players = List(p1, p2),
    deck = d,
    turnOverField = t,
    currentPlayerIndex = 0
  )

  "PhaseState" should {

    "nextPhase" should {
      "return itself by default" in {
        val phaseState = new IPhaseState {
          override def nextPhase: IPhaseState = this
        }
        phaseState.nextPhase shouldBe theSameInstanceAs(phaseState)
      }
    }

    "startPhase" should {
      "return the same game by default" in {
        val phaseState = new IPhaseState {
          override def nextPhase: IPhaseState =
            this // Provide a default implementation
        }
        val updatedGame = phaseState.startPhase(initialGame)
        updatedGame shouldEqual initialGame
      }
    }

    "GameInitializationPhase" should {
      "nextPhase" should {
        "transition to PlayCardPhase" in {
          val gameInitializationPhase = new GameInitializationPhase()
          gameInitializationPhase.nextPhase shouldBe a[PlayCardPhase]
        }
      }

      "startPhase" should {
        "return the same game" in {
          val gameInitializationPhase = new GameInitializationPhase()
          val updatedGame = gameInitializationPhase.startPhase(initialGame)
          updatedGame shouldEqual initialGame
        }
      }
    }

    "PlayCardPhase" should {
      "transition to TradeAndPlantPhase in nextPhase" in {
        val playCardPhase = PlayCardPhase()
        playCardPhase.nextPhase shouldBe a[TradeAndPlantPhase]
      }

      "increment currentPlayerIndex in startPhase" in {
        val playCardPhase = PlayCardPhase()
        val updatedGame = playCardPhase.startPhase(initialGame)
        updatedGame.currentPlayerIndex shouldEqual 1
      }
    }

    "TradeAndPlantPhase" should {
      "transition to DrawCardsPhase in nextPhase" in {
        val tradeAndPlantPhase = TradeAndPlantPhase()
        tradeAndPlantPhase.nextPhase shouldBe a[DrawCardsPhase]
      }

      "invoke drawCardToTurnOverField in startPhase" in {
        val tradeAndPlantPhase = TradeAndPlantPhase()
        val updatedGame = tradeAndPlantPhase.startPhase(initialGame)
        updatedGame.turnOverField.cards.length should be(2)
      }
    }

    "DrawCardsPhase" should {
      "transition to PlayCardPhase in nextPhase" in {
        val drawCardsPhase = DrawCardsPhase()
        drawCardsPhase.nextPhase shouldBe a[PlayCardPhase]
      }

      "invoke playerDrawCardFromDeck in startPhase" in {
        val drawCardsPhase = DrawCardsPhase()
        val updatedGame = drawCardsPhase.startPhase(initialGame)
        updatedGame
          .players(updatedGame.currentPlayerIndex)
          .hand
          .cards
          .length should be(1)
      }
    }
  }
}

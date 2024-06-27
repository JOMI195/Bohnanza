package htwg.de.se.bohnanza.model.FileIOComponent

import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.BeanField
import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.Player
import de.htwg.se.bohnanza.model.GameComponent.HandComponent.Hand
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.Deck
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.TurnOverField
import de.htwg.se.bohnanza.model.GameComponent.Game
import de.htwg.se.bohnanza.model.FileIOComponent.FileIOJson
import play.api.libs.json.Json
import scalafx.scene.effect.BlendMode.Blue
import de.htwg.se.bohnanza.model.PhaseStateComponent.DrawCardsPhase
import de.htwg.se.bohnanza.model.PhaseStateComponent.GameInitializationPhase
import de.htwg.se.bohnanza.model.PhaseStateComponent.TradeAndPlantPhase
import de.htwg.se.bohnanza.model.PhaseStateComponent.PlayCardPhase
import play.api.libs.json.JsObject

val emptyHand = Hand(List.empty)
val initialPlayer =
  Player("Player1", List(BeanField(None)), 0, emptyHand)
val initialPlayers = List(initialPlayer)
val initialDeck = Deck(List(Bean.ChiliBean))
val initialTurnOverField = TurnOverField(List(Bean.BlueBean))
val game = Game(initialPlayers, 0, initialDeck, initialTurnOverField)
val phase = GameInitializationPhase()
val fileIOJson = FileIOJson()

class FileIOJsonSpec extends AnyWordSpec with Matchers {
  "FileIOJson" should {
    "save game state correctly as json" when {
      val player =
        Player("Player1", List(BeanField(None)), 0, Hand(List(Bean.BlueBean)))
      "return correct json representation of player" in {
        fileIOJson.playerToJson(player) shouldBe Json.obj(
          "name" -> "Player1",
          "beanFields" -> Json.arr(Json.obj("bean" -> "None", "quantity" -> 0)),
          "coins" -> 0,
          "hand" -> Json.obj("cards" -> Json.arr("BlueBean"))
        )
      }

      "return correct representation of beanField" in {
        fileIOJson.beanFieldToJson(
          BeanField(Some(Bean.BlueBean), 1)
        ) shouldBe Json.obj(
          "bean" -> "BlueBean",
          "quantity" -> 1
        )
      }

      "return correct representation of bean wrapped in Option" when {
        "bean is Some" in {
          fileIOJson.optionBeanToString(
            Some(Bean.BlueBean)
          ) shouldBe "BlueBean"
        }
        "bean is None" in {
          fileIOJson.optionBeanToString(
            None
          ) shouldBe "None"
        }
      }

      "return correct representation of hand" when {
        "hand is not empty" in {
          val hand = Hand(List(Bean.BlueBean, Bean.BlueBean))
          fileIOJson.handToJson(hand) shouldBe Json.obj(
            "cards" -> Json.arr("BlueBean", "BlueBean")
          )

        }
        "hand is empty" in {
          fileIOJson.handToJson(emptyHand) shouldBe Json.obj(
            "cards" -> Json.arr()
          )
        }
      }

      "return correct representation of deck" when {
        "deck is not empty" in {
          val deck = Deck(List(Bean.BlueBean, Bean.BlueBean))
          fileIOJson.deckToJson(deck) shouldBe Json.obj(
            "cards" -> Json.arr("BlueBean", "BlueBean")
          )

        }
        "deck is empty" in {
          val emptyDeck = Deck(List.empty)
          fileIOJson.deckToJson(emptyDeck) shouldBe Json.obj(
            "cards" -> Json.arr()
          )
        }
      }

      "return correct representation of turnOverField" when {
        "turnOverField is not empty" in {
          val turnOverField = TurnOverField(List(Bean.BlueBean, Bean.BlueBean))
          fileIOJson.turnOverFieldToJson(turnOverField) shouldBe Json.obj(
            "cards" -> Json.arr("BlueBean", "BlueBean")
          )

        }
        "turnOverField is empty" in {
          val emptyTurnOverField = TurnOverField(List.empty)
          fileIOJson.turnOverFieldToJson(emptyTurnOverField) shouldBe Json.obj(
            "cards" -> Json.arr()
          )
        }
      }

      "Load correct associated phase from json" when {
        val drawCardsPhase = "DrawCardsPhase"
        val gameInitializationPhase = "GameInitializationPhase"
        val tradeAndPlantPhase = "TradeAndPlantPhase"
        val playCardPhase = "PlayCardPhase"
        "phase is DrawCardsPhase" in {
          fileIOJson
            .loadPhaseFromJson(drawCardsPhase)
            .isInstanceOf[DrawCardsPhase] shouldBe true

        }
        "phase is GameInitializationPhase" in {
          fileIOJson
            .loadPhaseFromJson(gameInitializationPhase)
            .isInstanceOf[GameInitializationPhase] shouldBe true

        }
        "phase is TradeAndPlantPhase" in {
          fileIOJson
            .loadPhaseFromJson(tradeAndPlantPhase)
            .isInstanceOf[TradeAndPlantPhase] shouldBe true

        }
        "phase is PlayCardPhase" in {
          fileIOJson
            .loadPhaseFromJson(playCardPhase)
            .isInstanceOf[PlayCardPhase] shouldBe true

        }
      }

      "save game representation correctly" in {
        val hand = Hand(List(Bean.BlueBean))
        val initialPlayer =
          Player("Player1", List(BeanField(None)), 0, hand)
        val initialPlayers = List(initialPlayer)
        val initialDeck = Deck(List(Bean.ChiliBean))
        val initialTurnOverField = TurnOverField(List(Bean.BlueBean))
        val game = Game(initialPlayers, 0, initialDeck, initialTurnOverField)
        fileIOJson.gameToJson(game) shouldBe Json.obj(
          "players" -> Json.arr(
            Json.obj(
              "name" -> "Player1",
              "beanFields" -> Json.arr(
                Json.obj("bean" -> "None", "quantity" -> 0)
              ),
              "coins" -> 0,
              "hand" -> Json.obj("cards" -> Json.arr("BlueBean"))
            )
          ),
          "currentPlayerIndex" -> 0,
          "deck" -> Json.obj("cards" -> Json.arr("ChiliBean")),
          "turnOverField" -> Json.obj(
            "cards" -> Json.arr("BlueBean")
          )
        )
      }

      "save game state correctly" in {
        fileIOJson.gameStateToJson(game, phase) shouldBe Json.obj(
          "game" -> fileIOJson.gameToJson(game),
          "phase" -> phase.getClass().getSimpleName()
        )
      }
    }

    "load game state successfully" when {
      "load game successfully" in {
        val jsonGame: JsObject = fileIOJson.gameToJson(game)
        fileIOJson.loadGameFromJson(jsonGame) shouldBe game
      }

      "load player" when {
        "player has no hand cards" in {
          val jsonPlayer: JsObject = fileIOJson.playerToJson(initialPlayer)
          fileIOJson.loadPlayerFromJson(jsonPlayer) shouldBe initialPlayer
        }
        "player has hand cards" in {
          val hand = Hand(List(Bean.BlueBean))
          val player =
            Player("Player1", List(BeanField(None)), 0, hand)
          val jsonPlayer: JsObject = fileIOJson.playerToJson(player)
          fileIOJson.loadPlayerFromJson(jsonPlayer) shouldBe player
        }

      }

      "load deck" in {
        val jsonDeck: JsObject = fileIOJson.deckToJson(initialDeck)
        fileIOJson.loadDeckFromJson(jsonDeck) shouldBe initialDeck
      }

      "load turnOverField" in {
        val jsonTurnOverField: JsObject =
          fileIOJson.turnOverFieldToJson(initialTurnOverField)
        fileIOJson.loadTurnOverFieldFromJson(
          jsonTurnOverField
        ) shouldBe initialTurnOverField
      }

      "load bean from json" when {
        "real bean" in {
          fileIOJson.loadBeanFromJson("BlueBean") shouldBe Some(Bean.BlueBean)
        }
        "fake bean" in {
          fileIOJson.loadBeanFromJson("FakeBean") shouldBe None
        }
      }

      "load complete game" in {
        fileIOJson.save(game, phase, filename = "testBohnanza.json")
        val (loadedGame, loadedPhase) =
          fileIOJson.load(filename = "testBohnanza.json")
        loadedGame shouldBe game
        loadedPhase.isInstanceOf[GameInitializationPhase] shouldBe true
      }
    }
  }
}

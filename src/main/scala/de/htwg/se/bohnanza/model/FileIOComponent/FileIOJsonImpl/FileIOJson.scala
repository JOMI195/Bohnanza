package de.htwg.se.bohnanza.model.FileIOComponent

import de.htwg.se.bohnanza.model.GameComponent.IGame
import de.htwg.se.bohnanza.model.PhaseStateComponent.IPhaseState
import java.io.PrintWriter
import java.io.File
import play.api.libs.json.Json
import play.api.libs.json.JsObject
import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.IPlayer
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.{IDeck, Deck}
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.ITurnOverField
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.IBeanField
import de.htwg.se.bohnanza.model.GameComponent.HandComponent.IHand
import de.htwg.se.bohnanza.model.GameComponent.Bean
import scala.io.Source
import play.api.libs.json.JsValue
import de.htwg.se.bohnanza.model.GameComponent.Game
import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.Player
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.BeanField
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.TurnOverField
import de.htwg.se.bohnanza.model.PhaseStateComponent.{
  DrawCardsPhase,
  GameInitializationPhase,
  TradeAndPlantPhase,
  PlayCardPhase
}
import de.htwg.se.bohnanza.model.GameComponent.HandComponent.Hand

class FileIOJson extends IFileIO {
  def save(
      game: IGame,
      phase: IPhaseState,
      filename: String = "bohnanza.json"
  ): Unit = {
    val pw = new PrintWriter(f"${SAVEGAMEDIR}${filename}")
    pw.write(Json.prettyPrint(gameStateToJson(game, phase)))
    pw.close()
  }

  def load(filename: String = "bohnanza.json"): (IGame, IPhaseState) = {
    val source: String =
      Source.fromFile(f"${SAVEGAMEDIR}${filename}").getLines.mkString
    val json = Json.parse(source)
    val game = loadGameFromJson(json("game"))
    val phase = loadPhaseFromJson(json("phase").as[String])
    return (game, phase)
  }

  def gameStateToJson(game: IGame, phase: IPhaseState): JsObject = {
    Json.obj(
      "game" -> gameToJson(game),
      "phase" -> phase.getClass().getSimpleName()
    )

  }

  def gameToJson(game: IGame): JsObject = {
    Json.obj(
      "players" -> game.players.map(player => playerToJson(player)).toList,
      "currentPlayerIndex" -> game.currentPlayerIndex,
      "deck" -> deckToJson(game.deck),
      "turnOverField" -> turnOverFieldToJson(game.turnOverField)
    )
  }

  def playerToJson(player: IPlayer): JsObject = {
    Json.obj(
      "name" -> player.name,
      "beanFields" -> player.beanFields
        .map(beanField => beanFieldToJson(beanField))
        .toList,
      "coins" -> player.coins,
      "hand" -> handToJson(player.hand)
    )

  }

  def beanFieldToJson(beanField: IBeanField): JsObject = {
    Json.obj(
      "bean" -> optionBeanToString(beanField.bean),
      "quantity" -> beanField.quantity
    )
  }

  def optionBeanToString(bean: Option[Bean]): String = {
    bean match {
      case None              => "None"
      case Some(checkedBean) => checkedBean.toString()
    }
  }

  def handToJson(hand: IHand): JsObject = {
    Json.obj(
      "cards" -> hand.cards.map(_.toString()).toList
    )
  }

  def deckToJson(deck: IDeck): JsObject = {
    Json.obj(
      "cards" -> deck.cards.map(_.toString()).toList
    )
  }

  def turnOverFieldToJson(turnOverField: ITurnOverField): JsObject = {
    Json.obj(
      "cards" -> turnOverField.cards.map(_.toString()).toList
    )
  }

  def loadPhaseFromJson(name: String): IPhaseState = {
    name match {
      case "DrawCardsPhase"          => DrawCardsPhase()
      case "GameInitializationPhase" => GameInitializationPhase()
      case "TradeAndPlantPhase"      => TradeAndPlantPhase()
      case "PlayCardPhase"           => PlayCardPhase()
    }
  }

  def loadGameFromJson(gameJson: JsValue): IGame = {
    val players = (gameJson("players"))
      .as[List[JsValue]]
      .map(jsonPlayer => loadPlayerFromJson(jsonPlayer))
      .toList
    val currentPlayerIndex = (gameJson("currentPlayerIndex")).as[Int]
    val deck = loadDeckFromJson(gameJson("deck"))
    val turnOverField = loadTurnOverFieldFromJson(gameJson("turnOverField"))
    Game(players, currentPlayerIndex, deck, turnOverField)
  }

  def loadPlayerFromJson(playerJson: JsValue): IPlayer = {
    val beanFields =
      playerJson("beanFields")
        .as[List[JsValue]]
        .map(beanField =>
          BeanField(
            loadBeanFromJson(beanField("bean").as[String]),
            beanField("quantity").as[Int]
          )
        )
        .toList
    val name = playerJson("name").as[String]
    val coins = playerJson("coins").as[Int]
    val jsonHand = playerJson("hand")
    val handCards = jsonHand("cards")
      .as[List[JsValue]]
      .flatMap(jsonBean => loadBeanFromJson(jsonBean.as[String]))
    val hand = Hand(handCards)
    Player(name, beanFields, coins, hand)
  }

  def loadDeckFromJson(deckJson: JsValue): IDeck = {
    val cards = deckJson("cards")
      .as[List[JsValue]]
      .flatMap(jsonBean => loadBeanFromJson(jsonBean.as[String]))
    Deck(cards)
  }

  def loadTurnOverFieldFromJson(
      turnOverFieldJson: JsValue
  ): ITurnOverField = {
    val cards = turnOverFieldJson("cards")
      .as[List[JsValue]]
      .flatMap(jsonBean => loadBeanFromJson(jsonBean.as[String]))
    TurnOverField(cards)
  }

  private val beansByName: Map[String, Bean] =
    Bean.values.map(bean => bean.toString() -> bean).toMap

  def loadBeanFromJson(name: String): Option[Bean] = beansByName.get(name)
}

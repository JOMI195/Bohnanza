package de.htwg.se.bohnanza.model.FileIOComponent

import de.htwg.se.bohnanza.model.FileIOComponent.IFileIO
import de.htwg.se.bohnanza.model.GameComponent.IGame
import de.htwg.se.bohnanza.model.PhaseStateComponent.IPhaseState
import scala.xml.Elem
import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.IPlayer
import de.htwg.se.bohnanza.model.GameComponent.HandComponent.IHand
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.IBeanField
import de.htwg.se.bohnanza.model.GameComponent.Bean
import java.io.PrintWriter
import java.io.File
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.ITurnOverField
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.IDeck
import de.htwg.se.bohnanza.model.GameComponent.Game
import scala.xml.Node
import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.Player
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.TurnOverField
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.Deck
import de.htwg.se.bohnanza.model.GameComponent.HandComponent.Hand
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.BeanField
import de.htwg.se.bohnanza.model.PhaseStateComponent.GameInitializationPhase
import de.htwg.se.bohnanza.model.PhaseStateComponent.DrawCardsPhase
import de.htwg.se.bohnanza.model.PhaseStateComponent.PlayCardPhase
import de.htwg.se.bohnanza.model.PhaseStateComponent.TradeAndPlantPhase
import scala.xml.XML

class FileIOXml extends IFileIO {
  def load(filename: String = "bohnanza.xml"): (IGame, IPhaseState) = {
    val xml = XML.loadFile(f"${SAVEGAMEDIR}${filename}")
    val game = gameFromXml((xml \ "game").head)
    val phase = phaseFromXml((xml \ "phase").head)
    (game, phase)
  }

  def save(
      game: IGame,
      phase: IPhaseState,
      filename: String = "bohnanza.xml"
  ): Unit = {
    val pw = new PrintWriter(new File(f"${SAVEGAMEDIR}${filename}"))
    val prettyPrinter = new scala.xml.PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(bohnanzaGameToXml(game, phase))
    pw.write(xml)
    pw.close()
  }

  // ---------- Bean ----------
  val beansByShortName: Map[String, Bean] =
    Bean.values.map(bean => bean.toString() -> bean).toMap

  def getBeanByName(name: String): Option[Bean] =
    beansByShortName.get(name)

  def beanToXml(bean: Bean): Elem = {
    <bean>{bean.toString}</bean>
  }

  def beanFromXml(node: Node): Option[Bean] = {
    getBeanByName(node.text)
  }

  // ---------- BeanField ----------
  def beanFieldToXml(beanField: IBeanField): Elem = {
    <beanField>
        {beanField.bean.map(beanToXml).getOrElse(<None/>)}
      <quantity>{beanField.quantity}</quantity>
    </beanField>
  }

  def beanFieldFromXml(node: Node): IBeanField = {
    val bean = (node \ "bean").headOption.flatMap(beanFromXml)
    val quantity = (node \ "quantity").text.toInt
    BeanField(bean, quantity)
  }

  // ---------- Hand ----------
  def handToXml(hand: IHand): Elem = {
    <cards>{hand.cards.map { card => beanToXml(card) }}</cards>
  }

  def handFromXml(node: Node): IHand = {
    val cards = (node \ "cards" \ "bean").map(beanFromXml).flatten.toList
    Hand(cards)
  }

  // ---------- Deck ----------
  def deckToXml(deck: IDeck): Elem = {
    <cards>{deck.cards.map { card => beanToXml(card) }}</cards>
  }

  def deckFromXml(node: Node): IDeck = {
    val cards = (node \ "cards" \ "bean").map(beanFromXml).flatten.toList
    Deck(cards)
  }

  // ---------- TurnoverField ----------
  def turnOverFieldToXml(turnOverField: ITurnOverField): Elem = {
    <cards>{turnOverField.cards.map { card => beanToXml(card) }}</cards>
  }

  def turnOverFieldFromXml(node: Node): ITurnOverField = {
    val cards = (node \ "cards" \ "bean").map(beanFromXml).flatten.toList
    TurnOverField(cards)
  }

  // ---------- Player ----------
  def playerToXml(player: IPlayer): Elem = {
    <player>
        <name>{player.name}</name>
        <coins>{player.coins}</coins>
        <beanFields>
        {player.beanFields.map { beanField => beanFieldToXml(beanField) }}   
        </beanFields>
        <hand>{handToXml(player.hand)}</hand>
    </player>
  }

  def playerFromXml(node: Node): IPlayer = {
    val name = (node \ "name").text
    val beanFields =
      (node \ "beanFields" \ "beanField").map(beanFieldFromXml).toList
    val coins = (node \ "coins").text.toInt
    val hand = handFromXml((node \ "hand").head)
    Player(name, beanFields, coins, hand)
  }

  // ---------- Game ----------
  def gameToXml(game: IGame): Elem = {
    <game>
        <players>{game.players.map { player => playerToXml(player) }}</players>
        <currentPlayerIndex>{game.currentPlayerIndex}</currentPlayerIndex>
        <deck>{deckToXml(game.deck)}</deck>
        <turnOverField>{turnOverFieldToXml(game.turnOverField)}</turnOverField>
    </game>
  }

  def gameFromXml(node: Node): IGame = {
    val players = (node \ "players" \ "player").map(playerFromXml).toList
    val currentPlayerIndex = (node \ "currentPlayerIndex").text.toInt
    val deck = deckFromXml((node \ "deck").head)
    val turnOverField = turnOverFieldFromXml((node \ "turnOverField").head)
    Game(players, currentPlayerIndex, deck, turnOverField)
  }

  // ---------- Phase ----------
  def phaseToXml(phase: IPhaseState): Elem = {
    <phase>{phase.getClass().getSimpleName()}</phase>
  }

  def phaseFromXml(node: Node): IPhaseState = {
    (node.text.trim match {
      case "GameInitializationPhase" => GameInitializationPhase()
      case "DrawCardsPhase"          => DrawCardsPhase()
      case "PlayCardPhase"           => PlayCardPhase()
      case "TradeAndPlantPhase"      => TradeAndPlantPhase()
    }).asInstanceOf[IPhaseState]
  }

  // ---------- Bohnanza game ----------
  def bohnanzaGameToXml(game: IGame, phase: IPhaseState): Elem = {
    <bohnanzaGame>
    {gameToXml(game)}
    {phaseToXml(phase)}
    </bohnanzaGame>
  }
}

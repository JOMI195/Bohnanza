package de.htwg.se.bohnanza

import com.google.inject.{AbstractModule, Guice, Injector, TypeLiteral}
import net.codingwell.scalaguice.ScalaModule

import de.htwg.se.bohnanza.controller.ControllerComponent.{
  IController,
  Controller
}
import de.htwg.se.bohnanza.model.ArgsHandlerComponent.{
  IArgumentHandler,
  ArgumentHandler
}
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.{
  IBeanField,
  BeanField
}
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.{IDeck, Deck}
import de.htwg.se.bohnanza.model.GameComponent.{IGame, Game}
import de.htwg.se.bohnanza.model.GameComponent.HandComponent.{IHand, Hand}
import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.{IPlayer, Player}
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.{
  ITurnOverField,
  TurnOverField
}
import de.htwg.se.bohnanza.model.PhaseStateComponent.DrawCardsPhase
import de.htwg.se.bohnanza.model.PhaseStateComponent.*
import com.google.inject.Scopes
import com.google.inject.Provides
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.FullDeckCreateStrategy

class BohnanzaModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind(classOf[IController]).to(classOf[Controller])
    bind(classOf[IArgumentHandler]).to(classOf[ArgumentHandler])
    bind(classOf[IBeanField]).to(classOf[BeanField])
    bind(classOf[IDeck]).to(classOf[Deck])
    bind(classOf[ITurnOverField]).to(classOf[TurnOverField])
    bind(classOf[IGame]).to(classOf[Game])
    bind(classOf[IHand]).to(classOf[Hand])
    bind(classOf[IPlayer]).to(classOf[Player])
    bind(classOf[IDrawCardsPhase]).to(classOf[DrawCardsPhase])
    bind(classOf[IGameInitializationPhase]).to(classOf[GameInitializationPhase])
    bind(classOf[IPlayCardPhase]).to(classOf[PlayCardPhase])
    bind(classOf[ITradeAndPlantPhase]).to(classOf[TradeAndPlantPhase])
  }

  @Provides
  def provideInitialGame(): IGame = {
    val d = FullDeckCreateStrategy().createDeck()
    // val d =
    //   SingleChiliBeanDeckCreateStrategy().createDeck() // for debugging purposes
    val t = TurnOverField(cards = List())
    Game(
      players = List.empty,
      deck = d,
      turnOverField = t,
      currentPlayerIndex = -1
    )
  }

  @Provides
  def provideInitialPhaseState(): IPhaseState = {
    GameInitializationPhase()
  }

}

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
import de.htwg.se.bohnanza.model.GameComponent.Bean
import com.google.inject.name.Names

class BohnanzaModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    /* WRAPPER-CLASSES CONFIGURATION */
    /* -- INTEGER (most of the default ints aure initialized with 0) */
    bind(classOf[Integer]).toInstance(0)

    /* MODELS CONFIGURATION */
    /* -- DECK */
    bind(classOf[IDeck]).toInstance(FullDeckCreateStrategy().createDeck())
    /* -- BEANFIELD */
    bind(classOf[IBeanField]).toInstance(new BeanField(bean = None))
    bind(new TypeLiteral[List[IBeanField]] {})
      .toInstance(List(BeanField(None), BeanField(None), BeanField(None)))
    /* -- HAND */
    bind(classOf[IHand]).toInstance(new Hand(cards = List.empty))
    /* -- TURNOVERFIELD */
    bind(classOf[ITurnOverField]).toInstance(
      new TurnOverField(cards = List.empty)
    )
    /* -- PLAYER */
    bind(classOf[IPlayer]).to(classOf[Player])
    bind(new TypeLiteral[List[IPlayer]] {})
      .toInstance(List.empty)
    /* -- GAME */
    bind(classOf[IGame]).to(classOf[Game])
    bind(classOf[Int])
      .annotatedWith(Names.named("currentPlayerIndex"))
      .toInstance(-1)

    /* CONTROLLER CONFIGURATION */
    bind(classOf[IController]).to(classOf[Controller])

    /* ARGUMENTHANDLER CONFIGURATION */
    bind(classOf[IArgumentHandler]).to(classOf[ArgumentHandler])

    /* PHASES CONFIGURATION */
    bind(classOf[IPhaseState]).toInstance(GameInitializationPhase())
    bind(classOf[IDrawCardsPhase]).to(classOf[DrawCardsPhase])
    bind(classOf[IGameInitializationPhase]).to(classOf[GameInitializationPhase])
    bind(classOf[IPlayCardPhase]).to(classOf[PlayCardPhase])
    bind(classOf[ITradeAndPlantPhase]).to(classOf[TradeAndPlantPhase])
  }
}

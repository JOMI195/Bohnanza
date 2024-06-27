package de.htwg.se.bohnanza

import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.{
  FullDeckCreateStrategy,
  IDeck,
  Deck
}
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.{
  IBeanField,
  BeanField
}
import de.htwg.se.bohnanza.model.GameComponent.HandComponent.{Hand, IHand}
import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.{Player, IPlayer}
import de.htwg.se.bohnanza.model.GameComponent.{Game, IGame}
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.{
  ITurnOverField,
  TurnOverField
}
import de.htwg.se.bohnanza.controller.ControllerComponent.{
  Controller,
  IController
}
import de.htwg.se.bohnanza.model.PhaseStateComponent.{
  IPhaseState,
  GameInitializationPhase,
  IDrawCardsPhase,
  DrawCardsPhase,
  IGameInitializationPhase,
  IPlayCardPhase,
  PlayCardPhase,
  ITradeAndPlantPhase,
  TradeAndPlantPhase
}
import de.htwg.se.bohnanza.model.ArgsHandlerComponent.{
  IArgumentHandler,
  ArgumentHandler
}
import de.htwg.se.bohnanza.model.FileIOComponent.FileIOXml
import de.htwg.se.bohnanza.model.FileIOComponent.IFileIO

object BohanzaModule {
  given IDeck = FullDeckCreateStrategy().createDeck();
  given IBeanField = BeanField(bean = None)
  given IHand = Hand(cards = List.empty)
  given IPlayer = Player("default")
  given ITurnOverField = TurnOverField(cards = List())
  given IGame = Game(
    List.empty,
    deck = summon[IDeck],
    turnOverField = summon[ITurnOverField],
    currentPlayerIndex = -1
  )
  given IPhaseState = GameInitializationPhase()
  given IController = Controller(summon[IGame])
  given IArgumentHandler = ArgumentHandler()
  given IDrawCardsPhase = DrawCardsPhase()
  given IGameInitializationPhase = GameInitializationPhase()
  given IPlayCardPhase = PlayCardPhase()
  given ITradeAndPlantPhase = TradeAndPlantPhase()

  given IFileIO = FileIOXml()
}

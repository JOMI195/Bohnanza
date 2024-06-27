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

val initialPlayer =
  Player("Player1", List(BeanField(None)), 0, Hand(List.empty))
val initialPlayers = List(initialPlayer)
val initialDeck = Deck(List(Bean.ChiliBean))
val initialTurnOverField = TurnOverField(List(Bean.BlueBean))
val game = Game(initialPlayers, 0, initialDeck, initialTurnOverField)

class FileIOJson extends AnyWordSpec with Matchers {
  "FileIOJson" should {
    "save game state correctly as json" when {
      "return correct json representation of player" in {}
    }
  }
}

import scala.io.Source
import play.api.libs.json.Json
import de.htwg.se.bohnanza.model.FileIOComponent.FileIOJson
import de.htwg.se.bohnanza.model.PhaseStateComponent.GameInitializationPhase
import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.TurnOverField
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.Deck
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.BeanField
import de.htwg.se.bohnanza.model.GameComponent.HandComponent.Hand
import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.Player
import de.htwg.se.bohnanza.model.GameComponent.Game

// val initialPlayer =
//   Player("Player1", List(BeanField(None)), 0, Hand(List.empty))
// val initialPlayers = List(initialPlayer)
// val initialDeck = Deck(List(Bean.ChiliBean))
// val initialTurnOverField = TurnOverField(List(Bean.BlueBean))
// val game = Game(initialPlayers, 0, initialDeck, initialTurnOverField)
// val phase = GameInitializationPhase()

// val fileIO = FileIO()
// // fileIO.gameStateToJson(game, phase)

val testPlayer1 =
  Player("Player1", List(BeanField(Some(Bean.BlueBean))), 0, Hand(List.empty))
val testPlayer2 = Player(
  "Player2",
  List(BeanField(Some(Bean.ChiliBean))),
  0,
  Hand(List(Bean.BlueBean))
)
val testPlayers = List(testPlayer1, testPlayer2)
val testDeck = Deck(List(Bean.ChiliBean))
val testTurnOverField = TurnOverField(List(Bean.BlueBean, Bean.BlueBean))
val testGame = Game(testPlayers, 1, testDeck, testTurnOverField)
val testPhase = GameInitializationPhase()
// fileIO.gameStateToJson(testGame, testPhase)
// val path1 = "bohnanza1.json"
// val path2 = "bohnanza2.json"
// val fileIO = FileIOJson()
// fileIO.save(testGame, testPhase)
// val (game1, phase1) = fileIO.load(path1)
// fileIO.save(game1, phase1, path2)

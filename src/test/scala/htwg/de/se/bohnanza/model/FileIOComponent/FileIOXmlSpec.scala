import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.bohnanza.model.GameComponent._
import de.htwg.se.bohnanza.model.PhaseStateComponent._
import java.io.File
import scala.xml.XML
import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.Player
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.BeanField
import de.htwg.se.bohnanza.model.GameComponent.HandComponent.Hand
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.FullDeckCreateStrategy
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.TurnOverField
import de.htwg.se.bohnanza.model.FileIOComponent.FileIOXml
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.Deck

class FileIOXmlSpec extends AnyWordSpec with Matchers {
  private val SAVEGAMEDIR = "./savegame/"
  private val filePath = s"${SAVEGAMEDIR}bohnanza_test.xml"

  // Sample data for testing
  private val p1 = Player(
    name = "Player1",
    coins = 5,
    beanFields = List(BeanField(Some(Bean.BlueBean), 0), BeanField(None, 0)),
    hand = Hand(cards = List(Bean.BlueBean, Bean.GreenBean))
  )

  private val d = FullDeckCreateStrategy().createDeck()
  private val t = TurnOverField(cards = List(Bean.ChiliBean))

  private val initialGame = Game(
    players = List(p1),
    deck = d,
    turnOverField = t,
    currentPlayerIndex = 0
  )

  private val initialPhase = GameInitializationPhase()

  private val fileIO = new FileIOXml

  "FileIOXml" should {
    "correctly save and load the game state" in {
      // Save the game state
      fileIO.save(initialGame, initialPhase)

      // Check if the file exists
      val file = new File(filePath)
      file.exists() shouldBe true

      // Load the game state
      val (loadedGame, loadedPhase) = fileIO.load

      // Assert that loaded game and phase are equal to initial game and phase
      loadedGame shouldBe initialGame
      loadedPhase shouldBe initialPhase
    }

    "correctly convert Bean to XML and back" in {
      val bean = Bean.BlueBean

      // Convert bean to XML and back
      val xml = fileIO.beanToXml(bean)
      val convertedBean = fileIO.beanFromXml(xml.head)

      convertedBean shouldBe Some(bean)
    }

    "correctly convert BeanField to XML and back" in {
      val beanField = BeanField(Some(Bean.BlueBean), quantity = 3)

      // Convert beanField to XML and back
      val xml = fileIO.beanFieldToXml(beanField)
      val convertedBeanField = fileIO.beanFieldFromXml(xml.head)

      convertedBeanField shouldBe beanField
    }

    "correctly convert Hand to XML and back" in {
      val hand = Hand(cards = List(Bean.BlueBean, Bean.GreenBean))

      // Convert hand to XML and back
      val xml = fileIO.handToXml(hand)
      val convertedHand = fileIO.handFromXml(xml.head)

      convertedHand shouldBe hand
    }

    "correctly convert Deck to XML and back" in {
      val deck = Deck(cards = List(Bean.BlueBean, Bean.GreenBean))

      // Convert deck to XML and back
      val xml = fileIO.deckToXml(deck)
      val convertedDeck = fileIO.deckFromXml(xml.head)

      convertedDeck shouldBe deck
    }

    "correctly convert TurnOverField to XML and back" in {
      val turnOverField =
        TurnOverField(cards = List(Bean.BlueBean, Bean.GreenBean))

      // Convert turnOverField to XML and back
      val xml = fileIO.turnOverFieldToXml(turnOverField)
      val convertedTurnOverField = fileIO.turnOverFieldFromXml(xml.head)

      convertedTurnOverField shouldBe turnOverField
    }

    "correctly convert Player to XML and back" in {
      val player = Player(
        name = "Player1",
        coins = 5,
        beanFields =
          List(BeanField(Some(Bean.BlueBean), 0), BeanField(None, 0)),
        hand = Hand(cards = List(Bean.BlueBean, Bean.GreenBean))
      )

      // Convert player to XML and back
      val xml = fileIO.playerToXml(player)
      val convertedPlayer = fileIO.playerFromXml(xml.head)

      convertedPlayer shouldBe player
    }

    "correctly convert Game to XML and back" in {
      val game = Game(
        players = List(p1),
        deck = d,
        turnOverField = t,
        currentPlayerIndex = 0
      )

      // Convert game to XML and back
      val xml = fileIO.gameToXml(game)
      val convertedGame = fileIO.gameFromXml(xml.head)

      convertedGame shouldBe game
    }

    "correctly convert PhaseState to XML and back" in {
      val phase = PlayCardPhase()

      // Convert phase to XML and back
      val xml = fileIO.phaseToXml(phase)
      val convertedPhase = fileIO.phaseFromXml(xml.head)

      convertedPhase shouldBe phase
    }
  }

  // Cleanup after tests
  def afterAll(): Unit = {
    val file = new File(filePath)
    if (file.exists()) {
      file.delete()
    }
  }
}

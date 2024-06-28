package de.htwg.se.bohnanza.model.FileIOComponent

import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.xml._

import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent._
import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent._
import de.htwg.se.bohnanza.model.GameComponent.HandComponent._
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent._
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent._
import de.htwg.se.bohnanza.model.GameComponent._
import de.htwg.se.bohnanza.model.PhaseStateComponent._
import scala.xml.Utility

class FileIOXmlSpec extends AnyWordSpec with Matchers {

  val fileIOXml = new FileIOXml()
  val emptyHand = Hand(List.empty)
  val initialPlayer = Player("Player1", List(BeanField(None)), 0, emptyHand)
  val initialPlayers = List(initialPlayer)
  val initialDeck = Deck(List(Bean.ChiliBean))
  val initialTurnOverField = TurnOverField(List(Bean.BlueBean))
  val game = Game(initialPlayers, 0, initialDeck, initialTurnOverField)
  val phase = GameInitializationPhase()

  "FileIOXml" should {

    "correctly convert beans to XML and back" in {
      val bean = Bean.BlueBean
      val beanXml = fileIOXml.beanToXml(bean)
      beanXml shouldBe <bean>BlueBean</bean>
      fileIOXml.beanFromXml(beanXml) shouldBe Some(bean)
    }

    "correctly convert bean fields to XML and back" in {
      val beanField = BeanField(Some(Bean.BlueBean), 1)
      val beanFieldXml = Utility.trim(fileIOXml.beanFieldToXml(beanField))
      val expectedResult = Utility.trim(
        <beanField><bean>BlueBean</bean><quantity>1</quantity></beanField>
      )
      beanFieldXml.toString should equal(expectedResult.toString)

      fileIOXml.beanFieldFromXml(beanFieldXml) shouldBe beanField
    }

    "correctly convert hands to XML and back" in {
      val hand = Hand(List(Bean.BlueBean, Bean.ChiliBean))
      val handXml = fileIOXml.handToXml(hand)
      Utility.trim(handXml) shouldBe Utility
        .trim(
          <cards>
          <bean>BlueBean</bean>
          <bean>ChiliBean</bean>
        </cards>
        )
      // fileIOXml.handFromXml(handXml).toString() shouldBe hand.toString()
    }

    "correctly convert hands to XML and is empty" in {
      val hand = Hand(List.empty)
      val handXml = fileIOXml.handToXml(hand)
      Utility.trim(handXml) shouldBe Utility
        .trim(
          <cards>
        </cards>
        )
      // fileIOXml.handFromXml(handXml) shouldBe hand
    }

    "correctly convert decks to XML and back" in {
      val deck = Deck(List(Bean.BlueBean, Bean.ChiliBean))
      val deckXml = fileIOXml.deckToXml(deck)
      Utility.trim(deckXml) shouldBe Utility.trim(
        <cards>
          <bean>BlueBean</bean>
          <bean>ChiliBean</bean>
        </cards>
      )
      // fileIOXml.deckFromXml(deckXml) shouldBe deck
    }

    "correctly convert turnover fields to XML and back" in {
      val turnOverField = TurnOverField(List(Bean.BlueBean, Bean.ChiliBean))
      val turnOverFieldXml = fileIOXml.turnOverFieldToXml(turnOverField)
      Utility.trim(turnOverFieldXml) shouldBe Utility.trim(
        <cards>
          <bean>BlueBean</bean>
          <bean>ChiliBean</bean>
        </cards>
      )
      // fileIOXml.turnOverFieldFromXml(turnOverFieldXml) shouldBe turnOverField
    }

    "correctly convert players to XML and back" in {
      val player = Player(
        "Player1",
        List(BeanField(Some(Bean.BlueBean), 1)),
        0,
        Hand(List(Bean.ChiliBean))
      )
      val playerXml = fileIOXml.playerToXml(player)
      Utility.trim(playerXml).toString shouldBe Utility
        .trim(
          <player>
          <name>Player1</name>
          <coins>0</coins>
          <beanFields>
            <beanField>
              <bean>BlueBean</bean>
              <quantity>1</quantity>
            </beanField>
          </beanFields>
          <hand>
            <cards>
              <bean>ChiliBean</bean>
            </cards>
          </hand>
        </player>
        )
        .toString
      // fileIOXml.playerFromXml(playerXml) shouldBe player
    }

    "correctly convert games to XML and back" in {
      val gameXml = fileIOXml.gameToXml(game)
      Utility.trim(gameXml).toString shouldBe Utility
        .trim(
          <game>
          <players>
            <player>
              <name>Player1</name>
              <coins>0</coins>
              <beanFields>
                <beanField>
                  <None/>
                  <quantity>0</quantity>
                </beanField>
              </beanFields>
              <hand>
                <cards/>
              </hand>
            </player>
          </players>
          <currentPlayerIndex>0</currentPlayerIndex>
          <deck>
            <cards>
              <bean>ChiliBean</bean>
            </cards>
          </deck>
          <turnOverField>
            <cards>
              <bean>BlueBean</bean>
            </cards>
          </turnOverField>
        </game>
        )
        .toString
      // fileIOXml.gameFromXml(gameXml) shouldBe game
    }

    "phase to xml" in {
      fileIOXml.phaseToXml(
        GameInitializationPhase()
      ) shouldBe <phase>GameInitializationPhase</phase>
    }

    "correctly convert phases from XML and back" when {
      "phase is GameInitializationPhase" in {
        fileIOXml
          .phaseFromXml(
            <phase>GameInitializationPhase</phase>
          )
          .isInstanceOf[GameInitializationPhase] shouldBe true
      }

      "phase is TradeAndPlantPhase" in {
        fileIOXml
          .phaseFromXml(
            <phase>TradeAndPlantPhase</phase>
          )
          .isInstanceOf[TradeAndPlantPhase] shouldBe true
      }

      "phase is PlayCardPhase" in {
        fileIOXml
          .phaseFromXml(
            <phase>PlayCardPhase</phase>
          )
          .isInstanceOf[PlayCardPhase] shouldBe true
      }

      "phase is DrawCardsPhase" in {
        fileIOXml
          .phaseFromXml(
            <phase>DrawCardsPhase</phase>
          )
          .isInstanceOf[DrawCardsPhase] shouldBe true
      }

      // fileIOXml.phaseFromXml(phaseXml) shouldBe phase
    }

    "correctly save and load game state to and from XML" in {
      val filename = "testBohnanza.xml"
      fileIOXml.save(game, phase, filename)
      val (loadedGame, loadedPhase) = fileIOXml.load(filename)
      loadedGame shouldBe game
      loadedPhase.isInstanceOf[GameInitializationPhase] shouldBe true
    }
  }
}

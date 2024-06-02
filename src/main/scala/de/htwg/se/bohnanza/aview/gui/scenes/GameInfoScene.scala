package bohnanza.aview.gui.scenes

import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{VBox}
import bohnanza.controller.Controller
import scalafx.geometry.Pos
import bohnanza.aview.gui.components.global.*
import bohnanza.aview.gui.Styles
import scala.util.Try
import scala.util.Success
import scala.util.Failure
import bohnanza.model.BeanField
import bohnanza.aview.gui.components.global.BeanFieldContainer

case class GameInfoScene(
    controller: Controller,
    windowWidth: Double,
    windowHeight: Double,
    onGoBackToGameButtonClick: () => Unit
) extends Scene(windowWidth, windowHeight) {

  val goBackToGameButton =
    GameButtonFactory.createGameButton("Go Back", width = 120, height = 50) {
      () => onGoBackToGameButtonClick()
    }
  goBackToGameButton.style = "-fx-font-size: 16; -fx-background-radius: 20;"

  val coins = Coins(controller, 0.5, 1.0)

  // currentPlayerIndex could be -1
  if (controller.game.currentPlayerIndex != -1) {
    val currentPlayerHandUnchecked = Try(
      Hand(cards =
        controller.game
          .players(0) // debug: hardcoded
          .hand
          .cards
          .map(card => Card(bean = card))
      )
    )

    val currentPlayerHand = currentPlayerHandUnchecked match {
      case Success(checkedHand) => checkedHand
      case Failure(e)           => Hand(cards = List.empty)
    }

    val currentDeck =
      Deck(cards =
        controller.game.deck.cards.map(card =>
          Card(bean = card, flipped = false)
        )
      )

    val firstBeanField = createBeanField(0)

    root = new VBox(20) {
      alignment = Pos.CENTER
      children = Seq(
        goBackToGameButton,
        coins,
        // currentPlayerHand,
        // currentDeck,
        firstBeanField
      )
    }
  }

  this.getStylesheets.add(Styles.baseCss)
  this.getStylesheets.add(Styles.gameCss)

  private def createBeanFieldCards(beanFieldIndex: Int): BeanFieldCards = {
    val beanField: BeanField =
      controller.game.players(0).beanFields(beanFieldIndex)
    val beanFieldCards: List[Card] = beanField.bean
      .map(bean => List.fill(beanField.quantity)(Card(bean = bean)))
      .getOrElse(List.empty)
    BeanFieldCards(cards = beanFieldCards)
  }

  def createBeanField(beanFieldIndex: Int): BeanFieldContainer = {
    val beanFieldCards = createBeanFieldCards(beanFieldIndex)
    BeanFieldContainer(
      beanFieldCards = beanFieldCards,
      beanFieldId = beanFieldIndex + 1 // the id = index + 1
    )
  }
}

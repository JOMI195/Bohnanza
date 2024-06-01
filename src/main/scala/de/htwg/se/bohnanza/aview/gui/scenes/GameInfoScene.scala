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
  val currentPlayerHandUnchecked = Try(
    Cards(cards =
      controller.game
        .players(controller.game.currentPlayerIndex)
        .hand
        .cards
        .map(card => BigCard(bean = card))
    )
  )

  val currentPlayerHand = currentPlayerHandUnchecked match {
    case Success(checkedHand) => checkedHand
    case Failure(e)           => Cards(cards = List.empty)
  }

  root = new VBox(20) {
    alignment = Pos.CENTER
    children = Seq(
      goBackToGameButton,
      coins,
      currentPlayerHand
    )
  }

  this.getStylesheets.add(Styles.baseCss)
  this.getStylesheets.add(Styles.gameCss)
}

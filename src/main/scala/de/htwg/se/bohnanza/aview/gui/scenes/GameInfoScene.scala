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
import bohnanza.aview.gui.components.gameInfo.PlayerInfo
import bohnanza.aview.gui.components.gameInfo.GameInfoGrid
import scalafx.geometry.Insets
import scalafx.scene.layout.StackPane

case class GameInfoScene(
    controller: Controller,
    windowWidth: Double,
    windowHeight: Double,
    onGoBackToGameButtonClick: () => Unit
) extends Scene(windowWidth, windowHeight) {

  val goBackToGameButton =
    GameButtonFactory.createGameButton("Go Back", width = 200, height = 40) {
      () => onGoBackToGameButtonClick()
    }
  goBackToGameButton.style = "-fx-font-size: 15;"

  // currentPlayerIndex could be -1
  if (controller.game.currentPlayerIndex != -1) {
    val gameInfoGrid = GameInfoGrid(players = controller.game.players)
    val turnOverFieldContainer = TurnOverFieldContainer(
      controller.game.turnOverField.cards
    )
    turnOverFieldContainer.alignment = Pos.CENTER
    root = new StackPane {
      children.addAll(
        turnOverFieldContainer,
        new VBox(0) {
          padding = Insets(5)
          children = Seq(
            goBackToGameButton,
            gameInfoGrid
          )
        }
      )
    }
  }

  this.getStylesheets.add(Styles.baseCss)
  this.getStylesheets.add(Styles.gameCss)

}

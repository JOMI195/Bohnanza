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
import scalafx.application.Platform

case class GameInfoScene(
    controller: Controller,
    windowWidth: Double,
    windowHeight: Double,
    onGoBackToGameButtonClick: () => Unit,
    moveToGamePlayerScene: (index: Int) => Unit
) extends Scene(windowWidth, windowHeight) {
  val bottomSnackbar =
    new BottomRightSnackbar(windowWidth, windowHeight)
  val topSnackbar = new TopCenterSnackbar(windowWidth, windowHeight)

  val goBackToGameButton =
    GameButtonFactory.createGameButton("Go Back", width = 200, height = 40) {
      () => onGoBackToGameButtonClick()
    }
  goBackToGameButton.style = "-fx-font-size: 15;"

  // currentPlayerIndex is -1 at the beginning
  if (controller.game.currentPlayerIndex != -1) {
    val gameInfoGrid = GameInfoGrid(
      players = controller.game.players,
      moveToGamePlayerScene = moveToGamePlayerScene
    )
    val turnOverFieldContainer = TurnOverFieldContainer(
      controller.game.turnOverField.cards,
      selectionManager = None,
      playerHand = None
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
        },
        bottomSnackbar,
        topSnackbar
      )
    }
  }

  def showBottomSnackbar(message: String) = {
    bottomSnackbar.showSnackbar(message)
  }

  def showTopSnackbar(message: String) = {
    topSnackbar.showSnackbar(message)
  }

  this.getStylesheets.add(Styles.baseCss)
  this.getStylesheets.add(Styles.gameCss)

}

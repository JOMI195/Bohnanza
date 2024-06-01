package bohnanza.aview.gui.scenes

import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{VBox}
import bohnanza.controller.Controller
import bohnanza.aview.gui.components.PlayersBar
import scalafx.geometry.Pos
import bohnanza.aview.gui.components.GameButtonFactory
import bohnanza.aview.gui.Styles

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

  root = new VBox(20) {
    alignment = Pos.CENTER
    children = Seq(
      goBackToGameButton
    )
  }

  this.getStylesheets.add(Styles.baseCss)
  this.getStylesheets.add(Styles.gameCss)
}

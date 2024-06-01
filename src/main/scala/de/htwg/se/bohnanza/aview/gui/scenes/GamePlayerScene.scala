package bohnanza.aview.gui.scenes

import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{VBox}
import bohnanza.controller.Controller
import scalafx.geometry.Pos
import bohnanza.aview.gui.Styles
import bohnanza.aview.gui.components.*

case class GamePlayerScene(
    controller: Controller,
    windowWidth: Double,
    windowHeight: Double,
    onGameInfoButtonClick: () => Unit
) extends Scene(windowWidth, windowHeight) {

  val gameInfoButton = GameButtonFactory.createGameButton(
    text = "Gameinfo",
    width = 350,
    height = 70
  ) { () =>
    onGameInfoButtonClick()
  }
  private val playersBar = new PlayersBar(controller)

  root = new VBox(20) {
    alignment = Pos.CENTER
    children = Seq(
      gameInfoButton,
      playersBar
    )
  }
  this.getStylesheets.add(Styles.baseCss)
  this.getStylesheets.add(Styles.gameCss)
}

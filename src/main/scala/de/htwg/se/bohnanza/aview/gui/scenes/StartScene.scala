package de.htwg.se.bohnanza.aview.gui.scenes

import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{VBox}
import scalafx.geometry.Pos

import de.htwg.se.bohnanza.controller.ControllerComponent.*
import de.htwg.se.bohnanza.aview.gui.Styles
import de.htwg.se.bohnanza.aview.gui.components.global.*
import de.htwg.se.bohnanza.aview.gui.utils.ImageUtils

case class StartScene(
    controller: IController,
    windowWidth: Double,
    windowHeight: Double,
    onGamePlayButtonClick: () => Unit
) extends Scene(windowWidth, windowHeight) {

  val resourceImagesUrl = "/images/start/"

  /** BUTTON */
  val playButton = GameButtonFactory.createGameButton(
    text = "Play",
    width = 350,
    height = 70
  ) { () =>
    onGamePlayButtonClick()
  }
  val playButtonBox = new VBox(0) {
    alignment = Pos.BOTTOM_CENTER
    children = Seq(playButton)
  }
  playButtonBox.prefHeight = 180

  /** IMAGE */
  val titleImage =
    ImageUtils.importImageAsView(resourceImagesUrl + "title.png", 0.6)
  val titleBox = new VBox(0) {
    alignment = Pos.BOTTOM_CENTER
    children = Seq(titleImage)
  }
  titleBox.prefHeight = windowHeight / 2

  root = new VBox(0) {
    alignment = Pos.TOP_CENTER
    children = Seq(
      titleBox,
      playButtonBox
    )
  }
  this.getStylesheets.add(Styles.baseCss)
  this.getStylesheets.add(Styles.startCss)
}

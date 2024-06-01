package bohnanza.aview.gui.scenes

import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{VBox}
import bohnanza.controller.Controller
import scalafx.geometry.Pos
import bohnanza.aview.gui.Styles
import bohnanza.aview.gui.utils.ImageUtils.importImage
import bohnanza.aview.gui.components.global.*
import bohnanza.aview.gui.components.playerCreation.CreatePlayerCard

case class PlayerCreateScene(
    controller: Controller,
    windowWidth: Double,
    windowHeight: Double
) extends Scene(windowWidth, windowHeight) {

  root = new VBox(0) {
    fillWidth = false
    alignment = Pos.CENTER
    children = Seq(
      CreatePlayerCard(controller)
    )
  }
  this.getStylesheets.add(Styles.baseCss)
  this.getStylesheets.add(Styles.playerCreationCss)
}

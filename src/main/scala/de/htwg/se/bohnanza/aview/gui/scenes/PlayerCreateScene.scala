package de.htwg.se.bohnanza.aview.gui.scenes

import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{VBox}
import scalafx.geometry.Pos

import de.htwg.se.bohnanza.controller.ControllerComponent.*
import de.htwg.se.bohnanza.aview.gui.Styles
import de.htwg.se.bohnanza.aview.gui.utils.ImageUtils.importImageAsView
import de.htwg.se.bohnanza.aview.gui.components.global.*
import de.htwg.se.bohnanza.aview.gui.components.playerCreation.CreatePlayerCard

case class PlayerCreateScene(
    controller: IController,
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

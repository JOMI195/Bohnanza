package bohnanza.aview.gui.components

import scalafx.scene.layout.VBox
import bohnanza.controller.Controller
import bohnanza.aview.gui.Gui
import bohnanza.aview.gui.utils.ImageUtils
import scalafx.scene.text.Text
import scalafx.geometry.Pos
import scalafx.scene.control.Label
import scalafx.geometry.Insets

class Coins(controller: Controller, scaleImage: Float, scaleFont: Float)
    extends VBox {
  prefHeight = 200
  prefWidth = 250
  fillWidth = false
  alignment = Pos.Center

  val coinImage = ImageUtils.importImage("/images/coins.png", scaleImage)
  val coinCountText: Text = new Text {
    text = "3 Coins"
    style = s"-fx-font-size: ${scaleFont * 20};" +
      "-fx-fill: #7A2626;"
  }

  val coinCountBox = new VBox(0) {
    fillWidth = false
    children = Seq(coinCountText)
    style = "-fx-background-color: #FEED5D;" + "-fx-background-radius: 36;"
    padding = Insets(8)
  }

  children.addAll(coinImage, coinCountBox)
}

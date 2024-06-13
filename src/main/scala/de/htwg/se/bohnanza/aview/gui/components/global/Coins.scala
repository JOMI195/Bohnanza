package bohnanza.aview.gui.components.global

import scalafx.scene.layout.VBox
import bohnanza.aview.gui.Gui
import bohnanza.aview.gui.utils.ImageUtils
import scalafx.scene.text.Text
import scalafx.geometry.Pos
import scalafx.scene.control.Label
import scalafx.geometry.Insets

class Coins(coins: Int, scaleImage: Float, scaleFont: Float) extends VBox {
  spacing = 5
  fillWidth = false
  alignment = Pos.CENTER

  val coinImage = ImageUtils.importImageAsView("/images/coins.png", scaleImage)
  val coinCountText: Text = new Text {
    text = s"${coins} Coins"
    style = s"-fx-font-size: ${scaleFont * 16};" +
      "-fx-fill: #7A2626;"
  }

  val coinCountBox = new VBox(0) {
    fillWidth = false
    children = Seq(coinCountText)
    style = "-fx-background-color: #FEED5D;" + "-fx-background-radius: 36;"
    padding = Insets(4, 8, 4, 8)
  }

  children.addAll(coinImage, coinCountBox)
}

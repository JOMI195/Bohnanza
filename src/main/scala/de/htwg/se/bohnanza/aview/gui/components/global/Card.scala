package bohnanza.aview.gui.components.global

import scalafx.scene.layout.HBox
import bohnanza.aview.gui.utils.ImageUtils
import bohnanza.model.Bean
import scalafx.scene.layout.StackPane
import scalafx.geometry.Pos
import scalafx.scene.layout.VBox
import scalafx.scene.layout.VBox.setMargin
import scalafx.Includes._

import scalafx.geometry.Insets
import scalafx.scene.Node
import scalafx.scene.image.ImageView
import scalafx.geometry.VerticalDirection.Up

case class Card(flipped: Boolean = true, bean: Bean, scaleFactor: Float = 0.3)
    extends HBox {
  val cardsPath = "/images/cards/"
  val cardImage = ImageUtils.importImage(
    imageUrl =
      if (flipped) cardsPath + s"${bean.shortName}-bean.png"
      else cardsPath + "card-flipped.png",
    scaleFactor = scaleFactor
  )

  style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5);"

  def flip(): Card = {
    copy(flipped = !flipped)
  }

  children.add(cardImage)
}

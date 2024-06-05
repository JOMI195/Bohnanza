package bohnanza.aview.gui.components.global

import bohnanza.aview.gui.utils.ImageUtils
import bohnanza.model.Bean
import scalafx.scene.layout.{StackPane, HBox, VBox}
import scalafx.scene.image.ImageView

val mainCardScaleFactor: Float = 0.35

case class Card(
    flipped: Boolean = true,
    bean: Bean,
    scaleFactor: Float = mainCardScaleFactor
) extends HBox {
  val cardsPath = "/images/cards/"
  val cardImage = ImageUtils.importImageAsView(
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

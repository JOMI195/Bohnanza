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

case class Card(flipped: Boolean = true, bean: Bean, scaleFactor: Float)
    extends HBox {
  val cardsPath = "/images/cards/"
  val cardImage = ImageUtils.importImage(
    imageUrl =
      if (flipped) cardsPath + s"${bean.shortName}-bean.png"
      else cardsPath + "card-flipped.png",
    scaleFactor = scaleFactor
  )

  def flip(): Card = {
    copy(flipped = !flipped)
  }

  children.add(cardImage)
}

class BigCard(
    flipped: Boolean = true,
    bean: Bean,
    scaleFactor: Float = 0.4
) extends Card(flipped, bean, scaleFactor)

class SmallCard(
    flipped: Boolean = true,
    bean: Bean,
    scaleFactor: Float = 0.3
) extends Card(flipped, bean, scaleFactor)

case class Cards(cards: List[Card]) extends HBox {
  alignment = Pos.Center
  val cardTranslation = 20

  val cardContainer = new StackPane {
    children = cards.reverse.zipWithIndex.map { case (card, index) =>
      card.translateX = index * cardTranslation
      card
    }
    translateX = -(cards.length - 1) * cardTranslation / 2
  }

  children = cardContainer

  def flippAllCards(): Cards = {
    copy(cards = cards.map(_.flip()))
  }
}

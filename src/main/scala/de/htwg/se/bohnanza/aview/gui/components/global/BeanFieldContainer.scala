package bohnanza.aview.gui.components.global

import scalafx.scene.layout.StackPane
import bohnanza.aview.gui.components.global.BeanFieldCards
import bohnanza.aview.gui.utils.ImageUtils
import bohnanza.aview.gui.components.global.mainCardScaleFactor
import scalafx.scene.layout.{VBox, Region}
import scalafx.geometry.Insets
import scalafx.geometry.Pos

class BeanFieldContainer(
    beanFieldCards: BeanFieldCards,
    beanFieldId: Int,
    scaleFactor: Float = mainCardScaleFactor
) extends StackPane {
  alignment = Pos.TOP_CENTER
  style = "-fx-background-color: FFCD92;"
  val beanFieldImage = ImageUtils.importImage(
    imageUrl = s"/images/cards/bean-field-$beanFieldId.png",
    scaleFactor = scaleFactor
  )

  val beanFieldCardsSpaced = new VBox {
    padding = Insets(50, 0, 0, 0)
    children = beanFieldCards
  }

  children.addAll(beanFieldImage, beanFieldCardsSpaced)
}

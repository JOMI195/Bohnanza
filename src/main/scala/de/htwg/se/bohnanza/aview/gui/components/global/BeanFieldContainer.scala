package bohnanza.aview.gui.components.global

import scalafx.scene.layout.StackPane
import bohnanza.aview.gui.components.global.BeanFieldCards
import bohnanza.aview.gui.utils.ImageUtils
import bohnanza.aview.gui.components.global.mainCardScaleFactor
import scalafx.scene.layout.{VBox, Region}

class BeanFieldContainer(beanFieldCards: BeanFieldCards, beanFieldId: Int)
    extends StackPane {
  val beanFieldImage = ImageUtils.importImage(
    imageUrl = s"/images/cards/bean-field-$beanFieldId.png",
    scaleFactor = mainCardScaleFactor
  )
  val spacer = new Region {
    prefHeight = 96
  }

  val beanFieldCardsSpaced = new VBox {
    children = List(spacer, beanFieldCards)
  }

  children.addAll(beanFieldImage, beanFieldCardsSpaced)
}

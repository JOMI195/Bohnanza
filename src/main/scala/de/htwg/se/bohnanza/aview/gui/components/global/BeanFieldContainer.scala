package bohnanza.aview.gui.components.global

import scalafx.scene.layout.StackPane
import bohnanza.aview.gui.components.global.BeanFieldCards
import bohnanza.aview.gui.utils.ImageUtils
import bohnanza.aview.gui.components.global.mainCardScaleFactor
import scalafx.scene.layout.{VBox, Region}
import scalafx.geometry.Insets
import scalafx.geometry.Pos
import bohnanza.aview.gui.model.SelectionManager
import scalafx.scene.input.MouseEvent
import scalafx.Includes._

class BeanFieldContainer(
    beanFieldCards: BeanFieldCards,
    beanFieldId: Int,
    scaleFactor: Float = mainCardScaleFactor,
    selectionManager: Option[SelectionManager]
) extends StackPane {
  alignment = Pos.TOP_CENTER
  style = "-fx-background-color: FFCD92;"
  val beanFieldImage = ImageUtils.importImageAsView(
    imageUrl = s"/images/cards/bean-field-$beanFieldId.png",
    scaleFactor = scaleFactor
  )
  val defaultBeanFieldStyle =
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5);"
  beanFieldImage.style = defaultBeanFieldStyle

  val beanFieldCardsSpaced = new VBox {
    padding = Insets(50, 0, 0, 0)
    children = beanFieldCards
  }

  onMouseClicked = (e: MouseEvent) => {
    selectionManager match {
      case None =>
      case Some(checkedSelectionManager) => {
        checkedSelectionManager.selectedBeanFieldIndex = beanFieldId - 1
        style =
          "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5); -fx-border-color: black; -fx-border-width: 2;"
      }
    }
  }

  children.addAll(beanFieldImage, beanFieldCardsSpaced)
}

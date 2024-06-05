package bohnanza.aview.gui.components.global

import scalafx.scene.layout.HBox
import scalafx.scene.layout.StackPane
import scalafx.geometry.Pos
import bohnanza.model.Bean
import bohnanza.aview.gui.utils.ImageUtils

class TurnOverFieldContainer(
    cards: List[Bean],
    scaleFactor: Float = mainCardScaleFactor
) extends HBox(10) {
  fillHeight = false

  val turnOverFieldCardPath = "/images/cards/"
  val turnOverFieldImage1 = ImageUtils.importImageAsView(
    imageUrl = turnOverFieldCardPath + "turnoverField.png",
    scaleFactor = scaleFactor
  )
  turnOverFieldImage1.style =
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5);"
  val turnOverFieldImage2 = ImageUtils.importImageAsView(
    imageUrl = turnOverFieldCardPath + "turnoverField.png",
    scaleFactor = scaleFactor
  )
  turnOverFieldImage2.style =
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5);"

  val stackPane1 = new StackPane {
    children.add(turnOverFieldImage1)
  }
  val stackPane2 = new StackPane {
    children.add(turnOverFieldImage2)
  }

  if (cards.nonEmpty) {
    val card1 = Card(bean = cards(0), scaleFactor = scaleFactor)
    card1.translateY = 50
    stackPane1.children.add(card1)

    if (cards.length > 1) {
      val card2 = Card(bean = cards(1), scaleFactor = scaleFactor)
      card2.translateY = 50
      stackPane2.children.add(card2)
    }
  }

  children.addAll(stackPane1, stackPane2)

}

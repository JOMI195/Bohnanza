package bohnanza.aview.gui.components.global

import scalafx.scene.layout.HBox
import scalafx.scene.layout.StackPane
import scalafx.geometry.Pos
import bohnanza.model.Bean
import bohnanza.aview.gui.utils.ImageUtils
import bohnanza.aview.gui.model.SelectionManager

class TurnOverFieldContainer(
    cards: List[Bean],
    scaleFactor: Float = mainCardScaleFactor,
    selectionManager: Option[SelectionManager]
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

  var turnOverFieldCards: List[Card] = List.fill(2)(null)
  if (cards.nonEmpty) {
    turnOverFieldCards = turnOverFieldCards.updated(
      0,
      Card(
        bean = cards(0),
        scaleFactor = scaleFactor,
        selectionManager = selectionManager,
        selectable = true,
        turnOverFieldCardIndex = 0
      )
    )
    turnOverFieldCards(0).translateY = 50
    stackPane1.children.add(turnOverFieldCards(0))

    if (cards.length > 1) {
      turnOverFieldCards = turnOverFieldCards.updated(
        1,
        Card(
          bean = cards(1),
          scaleFactor = scaleFactor,
          selectionManager = selectionManager,
          selectable = true,
          turnOverFieldCardIndex = 1
        )
      )
      turnOverFieldCards(1).translateY = 50
      stackPane2.children.add(turnOverFieldCards(1))
    }
  }

  def deselect(): Unit = {
    selectionManager match {
      case None =>
      case Some(checkedSelectionManager) => {
        if (checkedSelectionManager.selectedTurnOverFieldIndex == 0) {
          turnOverFieldCards(0).deselect()
        } else if (checkedSelectionManager.selectedTurnOverFieldIndex == 1) {
          turnOverFieldCards(1).deselect()
        }
        checkedSelectionManager.selectedTurnOverFieldIndex = -1
      }
    }
  }

  children.addAll(stackPane1, stackPane2)

}

package bohnanza.aview.gui.components.global

import bohnanza.aview.gui.utils.ImageUtils
import bohnanza.model.Bean
import scalafx.scene.layout.{StackPane, HBox, VBox}
import scalafx.scene.image.ImageView
import scalafx.scene.input.MouseEvent
import scalafx.Includes._
import bohnanza.aview.gui.model.SelectionManager

val mainCardScaleFactor: Float = 0.35

case class Card(
    flipped: Boolean = true,
    handCard: Boolean = false,
    turnOverFieldCardIndex: Int = -1,
    selectable: Boolean = false,
    bean: Bean,
    scaleFactor: Float = mainCardScaleFactor,
    selectionManager: Option[SelectionManager]
) extends HBox {

  var isSelected: Boolean = false
  var selectionMode =
    false // To ensure that if selectionMode activated to select the Card, debug first set to true!

  val cardsPath = "/images/cards/"
  val cardImage = ImageUtils.importImageAsView(
    imageUrl =
      if (flipped) cardsPath + s"${bean.shortName}-bean.png"
      else cardsPath + "card-flipped.png",
    scaleFactor = scaleFactor
  )

  val defaultCardStyle =
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5);"
  style = defaultCardStyle

  def selectOnClick(): Unit = {
    selectionManager match {
      case None =>
      case Some(checkedSelectionManager) => {
        if (handCard) {
          checkedSelectionManager.selectFromHand = true
        } else {
          checkedSelectionManager.selectedTurnOverFieldIndex =
            turnOverFieldCardIndex
        }

        isSelected = !isSelected
        style =
          "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5); -fx-border-color: black; -fx-border-width: 2;"
      }
    }

  }

  def deselect(): Unit = {
    if (selectable) {
      selectionManager match {
        case None =>
        case Some(checkedSelectionManager) => {
          // need to be careful since filter in gamePlayerScene runs this everytime a card is not selected
          checkedSelectionManager.selectFromHand = false
        }
      }

      isSelected = false
      style = defaultCardStyle
    }
  }

  children.add(cardImage)

  onMouseClicked = (e: MouseEvent) => {
    if (selectionMode && selectable) {
      if (isSelected) {
        deselect()
      } else {
        selectOnClick()
      }
    }
    e.consume()
  }

  def flip(): Card = {
    copy(flipped = !flipped)
  }
}

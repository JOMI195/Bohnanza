package bohnanza.aview.gui.components.global

import bohnanza.aview.gui.utils.ImageUtils
import bohnanza.model.Bean
import scalafx.scene.layout.{StackPane, HBox, VBox}
import scalafx.scene.image.ImageView
import scalafx.scene.input.MouseEvent
import scalafx.Includes._
import bohnanza.aview.gui.model.SelectionManager
import bohnanza.aview.gui.model.selectionStyle

val mainCardScaleFactor: Float = 0.35

case class Card(
    var selectedCards: List[Card],
    flipped: Boolean = true,
    handCard: Boolean = false,
    turnOverFieldCardIndex: Int = -1,
    selectable: Boolean = false,
    bean: Bean,
    scaleFactor: Float = mainCardScaleFactor,
    selectionManager: Option[SelectionManager]
) extends HBox {

  var isSelected: Boolean = false
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
    isSelected = !isSelected

    selectionManager match {
      case None =>
      case Some(checkedSelectionManager) => {
        if (isSelected) {
          if (handCard) {
            checkedSelectionManager.selectFromHand = true
            checkedSelectionManager.selectedTurnOverFieldIndex = -1
          } else {
            checkedSelectionManager.selectFromHand = false
            checkedSelectionManager.selectedTurnOverFieldIndex =
              turnOverFieldCardIndex
          }
          selectedCards
            .filter(_ != null)
            .foreach { card =>
              card.deselect()
            }
        }
        style = if (isSelected) selectionStyle else defaultCardStyle
      }
    }

  }

  def deselect(): Unit = {
    if (selectable) {
      selectionManager match {
        case None =>
        case Some(checkedSelectionManager) => {
          // need to be careful since filter in gamePlayerScene runs this everytime a card is not selected
          if (handCard) {
            checkedSelectionManager.selectFromHand = false
          } else {
            if (
              checkedSelectionManager.selectedTurnOverFieldIndex != turnOverFieldCardIndex
            ) {
              checkedSelectionManager.selectedTurnOverFieldIndex = -1
            }
          }
        }
      }

      isSelected = false
      style = defaultCardStyle
    }
  }

  children.add(cardImage)

  onMouseClicked = (e: MouseEvent) => {
    if (selectable) {
      if (isSelected) {
        deselect()
      } else {
        selectOnClick()
      }
    }
  }

  def flip(): Card = {
    copy(flipped = !flipped)
  }
}

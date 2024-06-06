package bohnanza.aview.gui.components.global

import bohnanza.aview.gui.utils.ImageUtils
import bohnanza.model.Bean
import scalafx.scene.layout.{StackPane, HBox, VBox}
import scalafx.scene.image.ImageView
import scalafx.scene.input.MouseEvent
import scalafx.Includes._
import bohnanza.aview.gui.model.SelectionManager
import bohnanza.aview.gui.model.selectionStyle
import scalafx.animation.Timeline
import scalafx.animation.KeyFrame
import scalafx.util.Duration
import scalafx.animation.KeyValue
import scalafx.scene.effect.ColorAdjust
import scalafx.animation.ScaleTransition

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

  val pulsateTransition = new ScaleTransition(Duration(1000), cardImage)
  pulsateTransition.fromX = 1.0
  pulsateTransition.toX = 1.05
  pulsateTransition.fromY = 1.0
  pulsateTransition.toY = 1.05
  pulsateTransition.cycleCount = ScaleTransition.Indefinite
  pulsateTransition.autoReverse = true

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
              if (handCard) {
                card.deselect()
              } else {
                card.deselectTurnOverFieldCards()
              }

            }
        }
        style = if (isSelected) selectionStyle else defaultCardStyle

        if (isSelected) {
          pulsateTransition.play()
        } else {
          pulsateTransition.stop()
          cardImage.scaleX = 1.0
          cardImage.scaleY = 1.0
        }
      }
    }
  }

  def deselectTurnOverFieldCards(): Unit = {
    isSelected = false
    style = defaultCardStyle
  }

  def deselect(): Unit = {
    if (selectable) {
      selectionManager match {
        case None =>
        case Some(checkedSelectionManager) => {
          // need to be careful since filter in gamePlayerScene runs this everytime a card is not selected
          isSelected = false
          style = defaultCardStyle
          if (handCard) {
            checkedSelectionManager.selectFromHand = false
          } else {
            checkedSelectionManager.selectedTurnOverFieldIndex = -1
          }
        }
      }

      isSelected = false
      style = defaultCardStyle
      pulsateTransition.stop()
      cardImage.scaleX = 1.0
      cardImage.scaleY = 1.0
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

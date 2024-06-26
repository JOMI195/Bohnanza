package de.htwg.se.bohnanza.aview.gui.components.global

import de.htwg.se.bohnanza.aview.gui.utils.ImageUtils
import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.aview.gui.model.SelectionManager
import de.htwg.se.bohnanza.aview.gui.model.selectionStyle

import scalafx.scene.layout.{StackPane, HBox, VBox}
import scalafx.scene.image.ImageView
import scalafx.scene.input.MouseEvent
import scalafx.Includes._
import scalafx.animation.Timeline
import scalafx.animation.KeyFrame
import scalafx.util.Duration
import scalafx.animation.KeyValue
import scalafx.scene.effect.ColorAdjust
import scalafx.animation.ScaleTransition

val mainCardScaleFactor: Float = 0.35

case class Card(
    flipped: Boolean = true,
    isHandCard: Boolean = false,
    turnOverFieldCardIndex: Int = -1,
    isSelectable: Boolean = false,
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

  children.add(cardImage)

  def flip(): Card = {
    copy(flipped = !flipped)
  }

  onMouseClicked = (e: MouseEvent) => {
    selectionManager match {
      case None => println("Selection Manager not initalized yet.")
      case Some(checkedSelectionManager) => {
        if (isSelectable) {
          if (isSelected) {
            checkedSelectionManager.deselect(
              isHandCard,
              turnOverFieldCardIndex
            )
            isSelected = false
          } else {
            checkedSelectionManager.selectOnClick(isSelected)
            isSelected = true
          }
        }
      }
    }

  }
}

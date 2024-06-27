package de.htwg.se.bohnanza.aview.gui.components.global

import de.htwg.se.bohnanza.aview.gui.utils.ImageUtils
import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.aview.gui.model.SelectionManager

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

val defaultCardStyle =
  "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5);"
val mainCardScaleFactor: Float = 0.4

abstract class Card(
    flipped: Boolean = true,
    isSelectable: Boolean = false,
    bean: Bean,
    scaleFactor: Float = mainCardScaleFactor
) extends HBox {
  var isSelected: Boolean = false
  var selectionManager: Option[SelectionManager]

  val cardsPath = "/images/cards/"
  val cardImage = ImageUtils.importImageAsView(
    imageUrl =
      if (flipped) cardsPath + s"${bean.shortName}-bean.png"
      else cardsPath + "card-flipped.png",
    scaleFactor = scaleFactor
  )

  val pulsateTransition = new ScaleTransition(Duration(1000), cardImage)
  pulsateTransition.fromX = 1.0
  pulsateTransition.toX = 1.05
  pulsateTransition.fromY = 1.0
  pulsateTransition.toY = 1.05
  pulsateTransition.cycleCount = ScaleTransition.Indefinite
  pulsateTransition.autoReverse = true

  style = defaultCardStyle

  children.add(cardImage)

  def flip(): Card
}

case class HandCard(
    flipped: Boolean = true,
    isSelectable: Boolean = false,
    bean: Bean,
    scaleFactor: Float = mainCardScaleFactor,
    var selectionManager: Option[SelectionManager]
) extends Card(flipped, isSelectable, bean, scaleFactor) {

  def flip(): Card = {
    copy(flipped = !flipped)
  }

  onMouseClicked = (e: MouseEvent) => {
    selectionManager match {
      case None => println("Debug: Selection Manager not initalized yet.")
      case Some(checkedSelectionManager) => {
        if (isSelectable) {
          checkedSelectionManager.selectHandCard()
        }
      }
    }
  }
}

case class TurnOverFieldCard(
    flipped: Boolean = true,
    isSelectable: Boolean = true,
    bean: Bean,
    scaleFactor: Float = mainCardScaleFactor,
    var selectionManager: Option[SelectionManager],
    turnOverFieldCardIndex: Int
) extends Card(flipped, isSelectable, bean, scaleFactor) {

  def flip(): Card = {
    copy(flipped = !flipped)
  }

  onMouseClicked = (e: MouseEvent) => {
    selectionManager match {
      case None => println("Debug: Selection Manager not initalized yet.")
      case Some(checkedSelectionManager) => {
        checkedSelectionManager.selectTurnOverFieldCard(
          turnOverFieldCardIndex
        )
      }
    }
  }
}

case class BeanFieldCard(
    flipped: Boolean = true,
    isSelectable: Boolean = false,
    bean: Bean,
    scaleFactor: Float = mainCardScaleFactor,
    var selectionManager: Option[SelectionManager] = None
) extends Card(flipped, isSelectable, bean, scaleFactor) {
  def flip(): Card = this
}

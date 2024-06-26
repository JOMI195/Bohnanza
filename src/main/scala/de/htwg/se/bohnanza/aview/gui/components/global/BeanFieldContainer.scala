package de.htwg.se.bohnanza.aview.gui.components.global

import de.htwg.se.bohnanza.aview.gui.components.global.BeanFieldCards
import de.htwg.se.bohnanza.aview.gui.utils.ImageUtils
import de.htwg.se.bohnanza.aview.gui.components.global.mainCardScaleFactor
import de.htwg.se.bohnanza.aview.gui.model.SelectionManager

import scalafx.scene.layout.StackPane
import scalafx.scene.layout.{VBox, Region}
import scalafx.geometry.Insets
import scalafx.geometry.Pos
import scalafx.scene.input.MouseEvent
import scalafx.Includes._
import scalafx.scene.effect.ColorAdjust
import scalafx.animation.Timeline
import scalafx.animation.ScaleTransition
import scalafx.util.Duration

class BeanFieldContainer(
    beanFieldCards: BeanFieldCards,
    beanFieldId: Int,
    scaleFactor: Float = mainCardScaleFactor,
    var selectionManager: Option[SelectionManager]
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

  val pulsateTransition = new ScaleTransition(Duration(1000), beanFieldImage)
  pulsateTransition.fromX = 1.0
  pulsateTransition.toX = 1.05
  pulsateTransition.fromY = 1.0
  pulsateTransition.toY = 1.05
  pulsateTransition.cycleCount = ScaleTransition.Indefinite
  pulsateTransition.autoReverse = true

  onMouseClicked = (e: MouseEvent) => {
    selectionManager match {
      case None =>
      case Some(checkedSelectionManager) => {
        checkedSelectionManager.selectBeanField(beanFieldId - 1)
      }
    }
  }

  children.addAll(beanFieldImage, beanFieldCardsSpaced)
}

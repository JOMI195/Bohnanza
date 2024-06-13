package de.htwg.se.bohnanza.aview.gui.components.global

import scalafx.animation.TranslateTransition
import scalafx.scene.control.Button
import scalafx.util.Duration
import scalafx.scene.effect.DropShadow
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color
import javafx.event.EventHandler

object GameButtonFactory {
  def createGameButton(
      text: String,
      width: Double,
      height: Double
  )(onFinishedAction: () => Unit): Button = {
    val button = new Button(text)
    button.prefWidth = width
    button.prefHeight = height
    button.getStyleClass.addAll("base-button")

    val translateDownAnimation: TranslateTransition = AnimationFactory
      .createTranslationAnimation(
        node = button,
        deltaX = 1,
        deltaY = 10,
        durationMillis = 15,
        cycleCount = 1,
        autoReverse = false
      )

    val translateUpAnimation: TranslateTransition = AnimationFactory
      .createTranslationAnimation(
        node = button,
        deltaX = -1,
        deltaY = -10,
        durationMillis = 15,
        cycleCount = 1,
        autoReverse = false
      )

    /*     val shadow = new DropShadow()
    shadow.color = Color.Gray
    shadow.radius = 5
    shadow.offsetX = 3
    shadow.offsetY = 3
    button.effect = shadow */

    // button.onAction = _ => translateAnimation.playFromStart()

    translateUpAnimation.onFinished = _ => {
      if (button.isHover) {
        onFinishedAction()
      }
    }

    var mouseStillOnButton = false

    button.onMousePressed = _ => {
      mouseStillOnButton = true
      translateDownAnimation.playFromStart()
    }

    button.onMouseReleased = _ => {
      mouseStillOnButton = false
      translateUpAnimation.playFromStart()
    }

    /*     button.onMousePressed = _ => translateDownAnimation.playFromStart()
    button.onMouseReleased = _ => translateUpAnimation.playFromStart()
    translateUpAnimation.onFinished = _ => onFinishedAction */

    button
  }
}

object AnimationFactory {

  def createTranslationAnimation(
      node: Button,
      deltaX: Double,
      deltaY: Double,
      durationMillis: Double,
      cycleCount: Int,
      autoReverse: Boolean
  ): TranslateTransition = {
    val translateTransition =
      new TranslateTransition(Duration(durationMillis), node)
    translateTransition.byX = deltaX
    translateTransition.byY = deltaY
    translateTransition.cycleCount = cycleCount
    translateTransition.autoReverse = autoReverse
    translateTransition
  }
}

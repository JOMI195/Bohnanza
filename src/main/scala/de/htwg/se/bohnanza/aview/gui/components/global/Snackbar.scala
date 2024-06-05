package bohnanza.aview.gui.components.global

import scalafx.animation.{KeyFrame, KeyValue, Timeline}
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.StackPane
import scalafx.util.Duration
import scalafx.Includes._
import bohnanza.aview.gui.utils.ImageUtils
import scalafx.scene.text.Text
import scalafx.geometry.Pos
import scalafx.scene.layout.VBox
import scalafx.scene.layout.Background
import scalafx.scene.layout.BackgroundImage
import scalafx.scene.layout.BackgroundRepeat
import scalafx.scene.layout.BackgroundPosition
import scalafx.scene.layout.BackgroundSize

class Snackbar(message: String, initialX: Double, initialY: Double)
    extends StackPane {
  val backgroundImage: Image =
    ImageUtils.importImage("/images/game/snackbarBanner.png")
  style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5);"
  val messageText = new Text(message)
  val closeButton = new Button("Close") {
    onAction = _ => hideSnackbar()
  }

  val overlayPane = new VBox {
    alignment = Pos.BOTTOM_RIGHT
    children = Seq(messageText, closeButton)
    background = new Background(
      Array(
        new BackgroundImage(
          backgroundImage,
          BackgroundRepeat.NoRepeat,
          BackgroundRepeat.NoRepeat,
          BackgroundPosition.Default,
          BackgroundSize.Default
        )
      )
    )
  }

  overlayPane.layoutX = initialX
  overlayPane.layoutY = initialY

  visible = false
  children.addAll(overlayPane)
  style = "-fx-padding: 10;"

  layoutX = initialX
  layoutY = initialY
  alignment = Pos.BOTTOM_RIGHT

  def showSnackbar(): Unit = {
    val showTimeline = new Timeline {
      keyFrames = Seq(
        at(0.s) { translateY -> 600 },
        at(0.5.s) { translateY -> 0 }
      )
      autoReverse = false
      cycleCount = 1
    }
    visible = true
    showTimeline.play()
  }

  def hideSnackbar(): Unit = {
    val hideTimeline = new Timeline {
      keyFrames = Seq(
        at(0.s) { translateY -> 0 },
        at(0.5.s) { translateY -> 600 }
      )
      autoReverse = false
      cycleCount = 1
    }
    hideTimeline.play()
    hideTimeline.onFinished = _ => visible = false
  }
}

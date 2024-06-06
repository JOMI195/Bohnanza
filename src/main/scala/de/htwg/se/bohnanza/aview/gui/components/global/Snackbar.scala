package bohnanza.aview.gui.components.global

import scalafx.Includes._
import scalafx.animation.TranslateTransition
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{Pane, StackPane}
import scalafx.util.Duration
import bohnanza.aview.gui.utils.ImageUtils
import scalafx.application.Platform
import scalafx.animation.Timeline
import scalafx.scene.effect.Glow
import scalafx.scene.effect.BlendMode
import scalafx.animation.KeyFrame
import scalafx.geometry.Pos

class BottomRightSnackbar(
    windowWidth: Double,
    windowHeight: Double
) extends Snackbar(
      x = windowWidth - 455,
      y = windowHeight - 150,
      width = 450,
      messageFontSize = 20,
      buttonFontSize = 12,
      animationTranslationY = 600
    ) {}

class TopCenterSnackbar(
    windowWidth: Double,
    windowHeight: Double
) extends Snackbar(
      x = windowWidth / 2 - 600 / 2,
      y = 10,
      width = 600,
      messageFontSize = 17,
      buttonFontSize = 15,
      animationTranslationY = -600
    ) {}

class Snackbar(
    x: Double,
    y: Double,
    width: Double,
    messageFontSize: Double,
    buttonFontSize: Double,
    animationTranslationY: Double
) extends Pane {
  val originalImage: Image =
    ImageUtils.importImage("/images/game/snackbarBanner.png")
  val newHeight = width * originalImage.height() / originalImage.width()
  val backgroundImageView = new ImageView(originalImage) {
    fitWidth = width
    fitHeight = newHeight
    style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5);"
  }

  val snackbarWidth = backgroundImageView.getFitWidth()
  val snackbarHeight = backgroundImageView.getFitHeight()
  val snackbarPadding = 80

  backgroundImageView.layoutX = x
  backgroundImageView.layoutY = y

  val message = new GameLabel("This is an info message", scalingFactor = 1)
  message.wrapText = true
  message.style =
    s"-fx-font-size: ${messageFontSize}; -fx-effect: dropshadow(gaussian, #C8C8C8, 2, 1, 0, 0);"

  val closeButtonWidth = 130
  val closeButtonHeight = 30
  val closeButton = GameButtonFactory.createGameButton(
    text = "Dismiss",
    width = closeButtonWidth,
    height = closeButtonHeight
  ) { () =>
    hideSnackbar()
  }
  closeButton.style = s"-fx-font-size: ${buttonFontSize}"

  backgroundImageView.visible = false
  message.visible = false
  closeButton.visible = false
  visible = false

  pickOnBounds = false
  children.addAll(backgroundImageView, closeButton, message)
  translateY = 0

  def showSnackbar(messageString: String): Unit = {
    val showTimeline = new Timeline {
      keyFrames = Seq(
        at(0.s) { translateY -> animationTranslationY },
        at(0.5.s) { translateY -> 0 }
      )
      autoReverse = false
      cycleCount = 1
    }
    backgroundImageView.visible = true
    message.visible = true
    closeButton.visible = true
    visible = true

    message.text = messageString

    showTimeline.play()
  }

  def hideSnackbar(): Unit = {
    val hideTimeline = new Timeline {
      keyFrames = Seq(
        at(0.s) { translateY -> 0 },
        at(0.5.s) { translateY -> animationTranslationY }
      )
      autoReverse = false
      cycleCount = 1
    }
    hideTimeline.play()
    hideTimeline.onFinished = _ => {
      backgroundImageView.visible = false
      message.visible = false
      closeButton.visible = false
      visible = false
    }
  }

  Platform.runLater(() => {
    message.layoutX = x + snackbarPadding
    message.layoutY = y + 25
    message.setWrapText(true)
    message.alignment = Pos.CENTER
    message.setMaxWidth(snackbarWidth - 2 * snackbarPadding)

    closeButton.layoutX = x + (snackbarWidth - closeButton.getWidth()) / 2
    closeButton.layoutY = y + snackbarHeight - closeButton.getHeight() - 20
  })
}

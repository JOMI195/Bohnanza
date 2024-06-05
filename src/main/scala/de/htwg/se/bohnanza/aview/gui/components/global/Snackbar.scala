package bohnanza.aview.gui.components.global

import scalafx.Includes._
import scalafx.animation.TranslateTransition
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{Pane, StackPane}
import scalafx.util.Duration
import bohnanza.aview.gui.utils.ImageUtils

class Snackbar(
    x: Double,
    y: Double
) extends Pane {
  // Load the background image
  val backgroundImageView =
    ImageUtils.importImageAsView("/images/game/snackbarBanner.png", 0.5)

  // Set the position of the background image
  backgroundImageView.layoutX = x
  backgroundImageView.layoutY = y

  val buttonText = "Dismiss"
  val button = GameButtonFactory.createGameButton(
    text = buttonText,
    width = 200,
    height = 40
  ) { () =>
    {}
  }
  button.style = s"-fx-font-size: ${15}"
  val textWidth =
    buttonText.length * 7 // Adjust the width multiplier as needed
  val textHeight = 20 // Adjust the height as needed
  button.layoutX = x + (backgroundImageView
    .fitWidth() - textWidth) / 2 // Center horizontally
  button.layoutY = y + (backgroundImageView
    .fitHeight() - textHeight) / 2 // Center vertically

  pickOnBounds = false
  // Add the background image and button to the StackPane
  children.addAll(backgroundImageView, button)
}

package bohnanza.aview.gui.components.global

import scalafx.beans.property.DoubleProperty
import scalafx.scene.control.Label
import scalafx.scene.text.Font

class GameLabel(initialText: String, scalingFactor: Double = 1.0)
    extends Label {

  text = initialText

  // Minimum and maximum font size
  private val minFontSize: Double = 10
  private val maxFontSize: Double = 100

  // Properties for label size
  private val labelWidth: DoubleProperty =
    new DoubleProperty(this, "labelWidth", 0)
  private val labelHeight: DoubleProperty =
    new DoubleProperty(this, "labelHeight", 0)

  // Add listeners to width and height properties
  this.widthProperty().addListener { (_, _, newWidth) =>
    labelWidth.set(newWidth.doubleValue())
    adjustFontSize()
  }

  this.heightProperty().addListener { (_, _, newHeight) =>
    labelHeight.set(newHeight.doubleValue())
    adjustFontSize()
  }

  // Method to calculate and set the appropriate font size
  private def adjustFontSize(): Unit = {
    val newFontSize = calculateFontSize(labelWidth.value, labelHeight.value)
    if (this.font.value != null) {
      this.font = Font(this.font.value.getFamily, newFontSize)
    } else {
      this.font = Font(newFontSize) // Fallback to default font family
    }
    this.style = s"-fx-font-size: ${newFontSize}; -fx-text-fill: #7A2626;"
  }

  // Basic calculation of font size based on label size and scaling factor
  private def calculateFontSize(width: Double, height: Double): Double = {
    val sizeBasedOnWidth = (width / 10) * scalingFactor
    val sizeBasedOnHeight = (height / 2) * scalingFactor
    val newSize = Math.min(sizeBasedOnWidth, sizeBasedOnHeight)
    Math.max(minFontSize, Math.min(newSize, maxFontSize))
  }
}

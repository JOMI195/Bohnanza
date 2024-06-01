package bohnanza.aview.gui.Utils

import scalafx.scene.image.{Image, ImageView}

object ImageUtils {

  def importImage(imageUrl: String, scaleFactor: Float): ImageView = {
    val image = new Image(getClass.getResourceAsStream(imageUrl))
    val imageView = new ImageView(image)
    imageView.fitWidth = image.width.value * scaleFactor
    imageView.fitHeight = image.height.value * scaleFactor
    imageView
  }
}

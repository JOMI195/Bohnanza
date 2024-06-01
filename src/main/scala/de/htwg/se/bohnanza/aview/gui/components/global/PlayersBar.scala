package bohnanza.aview.gui.components.global

import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.control.{Button, Label}
import scalafx.geometry.Insets
import scalafx.scene.paint.Color
import scalafx.scene.image.Image
import scalafx.scene.shape.Circle
import bohnanza.model.Player
import scalafx.geometry.Pos
import java.io.File
import scala.util.Random
import bohnanza.aview.gui.components.global.GameButtonFactory
import bohnanza.controller.Controller

class PlayersBar(controller: Controller) extends HBox {

  padding = Insets(10)
  spacing = 10

  controller.game.players.zipWithIndex.foreach { case (player, index) =>
    val avatar = new Circle {
      radius = 50
      style = s"-fx-fill: url('/images/game/playerAvatar${index + 1}.jpeg')"
    }

    val changePlayerGameButton = GameButtonFactory.createGameButton(
      text = player.name,
      width = 100,
      height = 10
    ) { () => }
    changePlayerGameButton.style = "-fx-font-size: 10;"

    val playerBox = new VBox {
      alignment = Pos.TOP_CENTER
      padding = Insets(5)
      spacing = 5
      children = Seq(
        avatar,
        changePlayerGameButton
      )
    }

    children.add(playerBox)
  }
}

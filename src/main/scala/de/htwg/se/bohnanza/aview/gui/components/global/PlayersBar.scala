package bohnanza.aview.gui.components.global

import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.control.Label
import scalafx.geometry.Pos
import bohnanza.model.Player
import bohnanza.aview.gui.components.global.GameButtonFactory
import bohnanza.controller.Controller
import scalafx.scene.text.Text
import bohnanza.aview.gui.components.global.PlayerAvatar
import bohnanza.aview.gui.components.global.GameLabel
import scalafx.geometry.Insets

class PlayersBar(controller: Controller) extends HBox {

  spacing = 1
  alignment = Pos.TOP_RIGHT

  controller.game.players.zipWithIndex.foreach { case (player, index) =>
    val playerAvatar = PlayerAvatar(
      player = player,
      playerIndex = index,
      scaleAvatar = 1,
      scaleFont = 1,
      onPlayerNameButtonClick = () => {}
    )

    // val currentPlayerText = new GameLabel("Current Player", scalingFactor = 0.5)

    val playerAvatarBox = new VBox {
      children = Seq(playerAvatar)
      spacing = 1
      padding = Insets(3)
    }

    if (index == controller.game.currentPlayerIndex) {
      playerAvatarBox.styleClass.add(
        "current-player"
      )
      children.add(playerAvatarBox)
    } else {
      children.add(playerAvatar)
    }
  }
}

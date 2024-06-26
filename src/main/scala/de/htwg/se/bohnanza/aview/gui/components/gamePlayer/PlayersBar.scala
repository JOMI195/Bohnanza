package de.htwg.se.bohnanza.aview.gui.components.gamePlayer

import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.IPlayer
import de.htwg.se.bohnanza.aview.gui.components.global.GameButtonFactory
import de.htwg.se.bohnanza.controller.ControllerComponent.*
import de.htwg.se.bohnanza.aview.gui.components.global.PlayerAvatar
import de.htwg.se.bohnanza.aview.gui.components.global.GameLabel

import scalafx.scene.text.Text
import scalafx.geometry.Insets
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.control.Label
import scalafx.geometry.Pos

class PlayersBar(
    controller: IController,
    moveToGamePlayerScene: (index: Int) => Unit
) extends HBox {

  spacing = 1
  alignment = Pos.TOP_RIGHT

  controller.game.players.zipWithIndex.foreach { case (player, index) =>
    val playerAvatar = PlayerAvatar(
      player = player,
      playerIndex = index,
      scaleAvatar = 1,
      scaleFont = 1,
      onPlayerNameButtonClick = () => moveToGamePlayerScene(index)
    )

    val currentPlayerText = new GameLabel("Current Player", scalingFactor = 0.5)

    val playerAvatarBox = new VBox {
      children = Seq(playerAvatar, currentPlayerText)
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

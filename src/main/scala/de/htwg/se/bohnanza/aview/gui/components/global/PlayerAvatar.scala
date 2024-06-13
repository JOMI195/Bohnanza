package de.htwg.se.bohnanza.aview.gui.components.global

import scalafx.scene.layout.VBox
import scalafx.scene.text.Text
import scalafx.geometry.Pos
import scalafx.scene.control.Label
import scalafx.geometry.Insets
import scalafx.scene.shape.Circle

import de.htwg.se.bohnanza.aview.gui.Gui
import de.htwg.se.bohnanza.aview.gui.utils.ImageUtils
import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.IPlayer

class PlayerAvatar(
    player: IPlayer,
    playerIndex: Int,
    scaleAvatar: Float,
    scaleFont: Float,
    onPlayerNameButtonClick: () => Unit
) extends VBox {
  val avatar = new Circle {
    radius = 50 * scaleAvatar
    style = s"-fx-fill: url('/images/game/playerAvatar${playerIndex + 1}.jpeg')"
  }
  avatar.styleClass.add(
    "player-avatar"
  )
  val changePlayerGameButton = GameButtonFactory.createGameButton(
    text = player.name,
    width = 100,
    height = 10
  ) { () => onPlayerNameButtonClick() }
  changePlayerGameButton.style = s"-fx-font-size: ${scaleFont * 12}"

  fillWidth = false
  alignment = Pos.TOP_CENTER
  padding = Insets(5)
  spacing = 5
  children = Seq(
    avatar,
    changePlayerGameButton
  )
}

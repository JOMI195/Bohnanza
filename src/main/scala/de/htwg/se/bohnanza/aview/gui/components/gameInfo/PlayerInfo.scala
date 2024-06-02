package bohnanza.aview.gui.components.gameInfo

import scalafx.scene.layout.{HBox, VBox}
import bohnanza.model.Player
import bohnanza.aview.gui.components.global.*
import bohnanza.model.BeanField
import bohnanza.Bohnanza.controller
import scalafx.geometry.Pos
import scalafx.scene.layout.Priority

class PlayerInfo(
    player: Player,
    playerIndex: Int,
    scaleAvatar: Float,
    scaleFont: Float,
    onPlayerNameButtonClick: () => Unit
) extends HBox(10) {
  fillHeight = true
  hgrow = Priority.Always
  vgrow = Priority.Always

  val playerStats = new VBox(10) {
    alignment = Pos.TOP_CENTER
    fillWidth = false
    val playerAvatar = PlayerAvatar(
      player,
      playerIndex,
      scaleAvatar,
      scaleFont,
      onPlayerNameButtonClick
    )
    val coins = Coins(player.coins, 0.4, 1.0)
    children.addAll(playerAvatar, coins)
  }

  val playerBeanFields = PlayerBeanFields(
    player = player,
    playerIndex = playerIndex
  )

  children = Seq(playerStats, playerBeanFields)
}

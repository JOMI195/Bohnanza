package de.htwg.se.bohnanza.aview.gui.components.gameInfo

import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.IPlayer
import de.htwg.se.bohnanza.aview.gui.components.global.*
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.BeanField
import de.htwg.se.bohnanza.aview.gui.model.SelectionManager

import scalafx.scene.layout.{HBox, VBox}
import scalafx.geometry.Pos
import scalafx.scene.layout.Priority

class PlayerInfo(
    player: IPlayer,
    playerIndex: Int,
    scaleAvatar: Float,
    scaleFont: Float,
    selectionManager: Option[SelectionManager],
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
    playerIndex = playerIndex,
    selectionManager = selectionManager
  )

  children = Seq(playerStats, playerBeanFields)
}

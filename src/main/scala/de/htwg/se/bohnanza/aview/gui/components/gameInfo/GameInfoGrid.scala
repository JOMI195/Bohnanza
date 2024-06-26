package de.htwg.se.bohnanza.aview.gui.components.gameInfo

import scalafx.scene.layout.{VBox, GridPane, HBox, Region, Priority}
import scalafx.geometry.Insets
import scalafx.geometry.Pos

import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.IPlayer
import de.htwg.se.bohnanza.aview.gui.components.gameInfo.PlayerInfo

class GameInfoGrid(
    players: List[IPlayer],
    moveToGamePlayerScene: (index: Int) => Unit
) extends GridPane {
  padding = Insets(10)
  vgrow = Priority.Always
  hgrow = Priority.Always

  private val alignments =
    Seq(Pos.TOP_LEFT, Pos.TOP_RIGHT, Pos.BOTTOM_LEFT, Pos.BOTTOM_RIGHT)

  players.zipWithIndex.foreach { case (player, index) =>
    val playerInfo = new PlayerInfo(
      player,
      index,
      scaleAvatar = 1.0,
      scaleFont = 1.0,
      onPlayerNameButtonClick = () => moveToGamePlayerScene(index)
    )

    val row = index / 2
    val col = index % 2
    playerInfo.alignment = alignments(index % 4)

    add(playerInfo, col, row)

    GridPane.setHgrow(playerInfo, Priority.Always)
    GridPane.setVgrow(playerInfo, Priority.Always)
  }
}

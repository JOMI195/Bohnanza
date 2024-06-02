package bohnanza.aview.gui.scenes

import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{VBox}
import bohnanza.controller.Controller
import scalafx.geometry.Pos
import bohnanza.aview.gui.Styles
import bohnanza.aview.gui.components.global.*
import bohnanza.aview.gui.components.gamePlayer.*
import scalafx.scene.layout.HBox
import scalafx.geometry.Insets
import scalafx.scene.layout.Region
import scalafx.scene.layout.Priority
import bohnanza.model.Player

case class GamePlayerScene(
    controller: Controller,
    windowWidth: Double,
    windowHeight: Double,
    currentPlayerViewIndex: Int,
    onGameInfoButtonClick: () => Unit,
    moveToGamePlayerScene: (index: Int) => Unit
) extends Scene(windowWidth, windowHeight) {

  val gameInfoButton = GameButtonFactory.createGameButton(
    text = "Gameinfo",
    width = 200,
    height = 40
  ) { () =>
    onGameInfoButtonClick()
  }
  gameInfoButton.style = s"-fx-font-size: ${15}"

  val playersBar = new PlayersBar(
    controller = controller,
    moveToGamePlayerScene = moveToGamePlayerScene
  )

  val currentViewPlayer: Player =
    if (controller.game.players.size > 0)
      controller.game.players(
        currentPlayerViewIndex
      )
    else Player("Dummy")

  val currentPlayerViewText: Label = new Label {
    text = s"${currentViewPlayer.name}s View"
    style = s"-fx-font-size: ${30};" +
      "-fx-text-fill: #7A2626;"
  }

  val spacer1 = new Region {
    HBox.setHgrow(this, Priority.Always)
  }
  val spacer2 = new Region {
    HBox.setHgrow(this, Priority.Always)
  }

  val hboxFlexibleSpacing = new HBox {
    children =
      Seq(gameInfoButton, spacer1, currentPlayerViewText, spacer2, playersBar)
  }

  root = new VBox {
    // spacing = 20
    padding = Insets(5)
    children = Seq(
      hboxFlexibleSpacing
    )
  }

  this.getStylesheets.add(Styles.baseCss)
  this.getStylesheets.add(Styles.gameCss)
}

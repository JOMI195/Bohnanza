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
import scalafx.scene.layout.Pane
import scalafx.scene.layout.StackPane
import bohnanza.aview.gui.SceneSnackbars

case class GamePlayerScene(
    controller: Controller,
    windowWidth: Double,
    windowHeight: Double,
    currentPlayerViewIndex: Int,
    onGameInfoButtonClick: () => Unit,
    moveToGamePlayerScene: (index: Int) => Unit
) extends Scene(windowWidth, windowHeight) {
  SceneSnackbars.bottomSnackbar =
    new BottomRightSnackbar(windowWidth, windowHeight)

  SceneSnackbars.topSnackbar = new TopCenterSnackbar(windowWidth, windowHeight)

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

  val topBarHSpacer1 = new Region {
    HBox.setHgrow(this, Priority.Always)
  }
  val topBarHSpacer2 = new Region {
    HBox.setHgrow(this, Priority.Always)
  }
  val mainAreaHSpacer1 = new Region {
    HBox.setHgrow(this, Priority.Always)
  }
  val mainAreaHSpacer2 = new Region {
    HBox.setHgrow(this, Priority.Always)
  }
  val midAreaVSpacer1 = new Region {
    VBox.setVgrow(this, Priority.Always)
  }

  val topInfobar = new HBox {
    children = Seq(
      gameInfoButton,
      topBarHSpacer1,
      currentPlayerViewText,
      topBarHSpacer2,
      playersBar
    )
  }

  val playerBeanFields = PlayerBeanFields(
    player = currentViewPlayer,
    playerIndex = currentPlayerViewIndex,
    scaleFactor = 0.4
  )

  val coins = Coins(currentViewPlayer.coins, 0.6, 1.5)

  val turnOverFieldContainer = TurnOverFieldContainer(
    controller.game.turnOverField.cards,
    scaleFactor = 0.4
  )

  val handcards: List[Card] = currentViewPlayer.hand.cards.map { bean =>
    Card(bean = bean, scaleFactor = 0.4)
  }
  val hand = Hand(cards = handcards)

  val leftElements = new VBox {
    alignment = Pos.TOP_LEFT
    children = Seq(playerBeanFields)
  }

  val midElements = new VBox {
    padding = Insets(60, 0, 0, 0)
    alignment = Pos.TOP_CENTER
    children = Seq(turnOverFieldContainer, midAreaVSpacer1, hand)
  }

  val rightElements = new VBox {
    alignment = Pos.BOTTOM_RIGHT
    children = Seq(coins)
  }

  val sceneMainArea = new HBox {
    vgrow = Priority.Always
    children = Seq(
      leftElements,
      mainAreaHSpacer1,
      midElements,
      mainAreaHSpacer2,
      rightElements
    )
  }

  root = new StackPane {
    vgrow = Priority.Always
    children = Seq(
      new VBox {
        padding = Insets(5)
        children = Seq(
          topInfobar,
          sceneMainArea
        )
      },
      SceneSnackbars.bottomSnackbar,
      SceneSnackbars.topSnackbar
    )
  }

  SceneSnackbars.bottomSnackbar.showSnackbar(
    "This is an overridden info message"
  )
  SceneSnackbars.topSnackbar.showSnackbar(
    "This is an overridden info message"
  )

  this.getStylesheets.add(Styles.baseCss)
  this.getStylesheets.add(Styles.gameCss)
}

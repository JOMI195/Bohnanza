package bohnanza.aview.gui.scenes

import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{VBox, HBox, Region, Priority, StackPane}
import de.htwg.se.bohnanza.controller.ControllerComponent.*
import scalafx.geometry.Pos
import bohnanza.aview.gui.Styles
import bohnanza.aview.gui.components.global.*
import bohnanza.aview.gui.components.gamePlayer.*
import scalafx.geometry.Insets
import bohnanza.model.Player
import scalafx.Includes._
import scalafx.scene.input.MouseEvent
import bohnanza.aview.gui.model.SelectionManager

case class GamePlayerScene(
    controller: IController,
    windowWidth: Double,
    windowHeight: Double,
    currentPlayerViewIndex: Int,
    onGameInfoButtonClick: () => Unit,
    moveToGamePlayerScene: (index: Int) => Unit,
    selectionManager: SelectionManager
) extends Scene(windowWidth, windowHeight) {
  val bottomSnackbar =
    new BottomRightSnackbar(windowWidth, windowHeight)
  val topSnackbar = new TopCenterSnackbar(windowWidth, windowHeight)

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

  val currentPhaseText: Label = new Label {
    text = s"Current Phase: ${controller.phase.getClass.getSimpleName}"
    style = s"-fx-font-size: ${20};" +
      "-fx-text-fill: #7A2626;"
  }

  val topInfoText = new VBox {
    alignment = Pos.TOP_CENTER
    spacing = 5
    children = Seq(currentPlayerViewText, currentPhaseText)
  }

  val leftAreaVSpacer1 = new Region {
    VBox.setVgrow(this, Priority.Always)
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
      topInfoText,
      topBarHSpacer2,
      playersBar
    )
  }

  val playerBeanFields = PlayerBeanFields(
    player = currentViewPlayer,
    playerIndex = currentPlayerViewIndex,
    scaleFactor = 0.4,
    selectionManager = Some(selectionManager)
  )

  val actions = Actions(
    controller = controller,
    onHarvestButtonClick = () => {
      if (selectionManager.selectedBeanFieldIndex == -1) {
        topSnackbar.showSnackbar(
          "Please select the bean field that you want to harvest."
        )
      } else {
        controller.harvest(
          currentPlayerViewIndex,
          selectionManager.selectedBeanFieldIndex
        )
      }
      deselectOnAction()
    },
    onPlantButtonClick = () => {
      if (
        selectionManager.selectedBeanFieldIndex == -1 && (!selectionManager.selectFromHand || selectionManager.selectedTurnOverFieldIndex == -1)
      )
        topSnackbar.showSnackbar(
          "Please select the bean and bean field that you want to plant on."
        )
      else {
        if (selectionManager.selectFromHand) {
          controller.plant(
            currentPlayerViewIndex,
            selectionManager.selectedBeanFieldIndex
          )
        } else {
          controller.take(
            currentPlayerViewIndex,
            selectionManager.selectedTurnOverFieldIndex,
            selectionManager.selectedBeanFieldIndex
          )
        }
      }
      deselectOnAction()
    },
    onDrawButtonClick = () => {
      controller.draw(currentPlayerViewIndex)
    }
  )

  val coins = Coins(currentViewPlayer.coins, 0.6, 1.5)

  val playerHand = PlayerHand(currentViewPlayer, selectionManager)

  val turnOverFieldContainer = TurnOverFieldContainer(
    controller.game.turnOverField.cards,
    scaleFactor = 0.4,
    selectionManager = Some(selectionManager),
    playerHand = Some(playerHand)
  )

  // need to be changed, since cards can be empty so there is no instance of selectedCard
  if (playerHand.currentViewPlayer.hand.cards.nonEmpty)
    playerHand.selectableCard.selectedCards =
      turnOverFieldContainer.getTurnOverFieldCards()

  val leftElements = new VBox {
    alignment = Pos.TOP_LEFT
    children = Seq(playerBeanFields, leftAreaVSpacer1, actions)
  }

  val midElements = new VBox {
    padding = Insets(60, 0, 0, 0)
    alignment = Pos.TOP_CENTER
    children = Seq(turnOverFieldContainer, midAreaVSpacer1, playerHand)
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
      bottomSnackbar,
      topSnackbar
    )
  }

  var lastSelectedCard = SelectedCard.None

  this.addEventFilter(
    MouseEvent.MouseClicked,
    (e: MouseEvent) => {
      val beanFieldContainer = "class javafx.scene.layout.VBox"
      val card = "class javafx.scene.image.ImageView"
      val button = "class javafx.scene.control.Button"
      val labeledText = "class com.sun.javafx.scene.control.skin.LabeledText"
      if (
        e.target != null && !e.target
          .getClass()
          .toString()
          .equals(beanFieldContainer)
        && !e.target.getClass().toString().equals(card)
        && !e.target.getClass().toString().equals(button)
        && !e.target.getClass().toString().equals(labeledText)
      ) {
        playerHand.hand.cards.foreach(_.deselect())
        turnOverFieldContainer.deselect()
        playerBeanFields.deselect()
      } else if (
        e.target
          .getClass()
          .toString()
          .equals(
            beanFieldContainer
          ) && selectionManager.selectedBeanFieldIndex != -1
      ) {
        playerBeanFields.deselect()
      }

    }
  )

  def deselectOnAction(): Unit = {
    playerHand.hand.cards.foreach(_.deselect())
    turnOverFieldContainer.deselect()
    playerBeanFields.deselect()

  }

  def showBottomSnackbar(message: String) = {
    bottomSnackbar.showSnackbar(message)
  }

  def showTopSnackbar(message: String) = {
    topSnackbar.showSnackbar(message)
  }

  this.getStylesheets.add(Styles.baseCss)
  this.getStylesheets.add(Styles.gameCss)
}

enum SelectedCard {
  case HandCard
  case TurnCard
  case None
}

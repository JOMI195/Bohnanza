package de.htwg.se.bohnanza.aview.gui

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.text.Font
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.application.Platform

import de.htwg.se.bohnanza.aview.gui.components.*
import de.htwg.se.bohnanza.util.Observer
import de.htwg.se.bohnanza.controller.ControllerComponent.*
import de.htwg.se.bohnanza.util.ObserverEvent
import de.htwg.se.bohnanza.model.ArgsHandlerComponent.HandlerResponse
import de.htwg.se.bohnanza.aview.gui.scenes.GamePlayerScene
import de.htwg.se.bohnanza.aview.gui.scenes.StartScene
import de.htwg.se.bohnanza.aview.gui.scenes.PlayerCreateScene
import de.htwg.se.bohnanza.aview.gui.scenes.GameInfoScene
import de.htwg.se.bohnanza.aview.gui.model.SelectionManager

object Styles {
  val baseCss = getClass.getResource("/styles/base.css").toExternalForm
  val startCss =
    getClass.getResource("/styles/start.css").toExternalForm
  val playerCreationCss =
    getClass.getResource("/styles/playerCreation.css").toExternalForm
  val gameCss = getClass.getResource("/styles/game.css").toExternalForm
}

class Gui(controller: IController) extends JFXApp3 with Observer {

  var startScene: StartScene = _

  var playerCreationScene: PlayerCreateScene = _

  var gamePlayerScene: GamePlayerScene = _

  var gameInfoScene: GameInfoScene = _

  private var currentPlayerViewIndex: Int = _

  controller.add(this)

  // ------------ Variables ---------------------------------------------------
  private val windowWidth = 1366
  private val windowHeight = 768

  // ------------ Load Fonts ------------------------------------------
  Font.loadFont(getClass.getResourceAsStream("/fonts/HoltwoodOneSC.ttf"), 50)

  // ------------ Funtions ----------------------------------------------------

  // ------------ Gui --------------------------------------------------------
  /** Defines and draws the layout of the Game GUI */
  override def start(): Unit = {
    startScene = StartScene(
      controller = controller,
      windowWidth = windowWidth,
      windowHeight = windowHeight,
      onGamePlayButtonClick = () => stage.setScene(playerCreationScene)
    )

    playerCreationScene = PlayerCreateScene(
      controller = controller,
      windowWidth = windowWidth,
      windowHeight = windowHeight
    )

    val selectionManager = SelectionManager()
    gamePlayerScene = GamePlayerScene(
      controller = controller,
      windowWidth = windowWidth,
      windowHeight = windowHeight,
      currentPlayerViewIndex = currentPlayerViewIndex,
      onGameInfoButtonClick = () => {
        updateControllerOfScenes()
        stage.setScene(gameInfoScene)
      },
      moveToGamePlayerScene = moveToGamePlayerScene,
      selectionManager = selectionManager
    )

    gameInfoScene = GameInfoScene(
      controller = controller,
      windowWidth = windowWidth,
      windowHeight = windowHeight,
      onGoBackToGameButtonClick = () => {
        updateControllerOfScenes()
        stage.setScene(gamePlayerScene)
      },
      moveToGamePlayerScene = moveToGamePlayerScene
    )

    // Primary Stage
    stage = new JFXApp3.PrimaryStage {
      resizable = false
      icons += new Image(getClass().getResourceAsStream("/images/gameIcon.png"))
      title = "Bohnanza"
      scene = startScene
    }
  }

  // ------------ Observer ---------------------------------------------------
  override def update(error: HandlerResponse): Unit = {
    error match {
      case HandlerResponse.BeanFieldIndexError => {
        Platform.runLater(() => {
          val message = "You don't have this bean field."
          gameInfoScene.showBottomSnackbar(message)
          gamePlayerScene.showBottomSnackbar(message)
        })
      }
      case HandlerResponse.PlayerIndexError => {
        Platform.runLater(() => {
          val message = "This player does not exist."
          gameInfoScene.showBottomSnackbar(message)
          gamePlayerScene.showBottomSnackbar(message)
        })
      }
      case HandlerResponse.CurrentPlayerIndexError => {
        Platform.runLater(() => {
          val message = "This player is not the current player."
          gameInfoScene.showBottomSnackbar(message)
          gamePlayerScene.showBottomSnackbar(message)
        })
      }
      case HandlerResponse.TurnOverFieldIndexError => {
        Platform.runLater(() => {
          val message = "You can't access a turn-over card with this index."
          gameInfoScene.showBottomSnackbar(message)
          gamePlayerScene.showBottomSnackbar(message)
        })
      }
      case HandlerResponse.HandIndexError => {
        Platform.runLater(() => {
          val message = "You don't have any cards to plant anymore."
          gameInfoScene.showBottomSnackbar(message)
          gamePlayerScene.showBottomSnackbar(message)
        })
      }
      case HandlerResponse.MethodError => {
        Platform.runLater(() => {
          val message = "You can't use this method in this phase."
          gameInfoScene.showBottomSnackbar(message)
          gamePlayerScene.showBottomSnackbar(message)
        })
      }
      case HandlerResponse.ArgsError =>
      case HandlerResponse.Success   =>
      case HandlerResponse.MissingPlayerCreationError => {
        Platform.runLater(() => {
          val message =
            "You can't go to the next phase because you didn't create any player yet."
          gameInfoScene.showBottomSnackbar(message)
          gamePlayerScene.showBottomSnackbar(message)
        })
      }
      case HandlerResponse.TakeInvalidPlantError => {
        Platform.runLater(() => {
          val message =
            "The bean from the turn over field does not match with the bean on your bean field."
          gameInfoScene.showBottomSnackbar(message)
          gamePlayerScene.showBottomSnackbar(message)
        })
      }
      case HandlerResponse.InvalidPlantError => {
        Platform.runLater(() => {
          val message =
            "The bean from your hand does not match with the bean on your bean field."
          gameInfoScene.showBottomSnackbar(message)
          gamePlayerScene.showBottomSnackbar(message)
        })
      }
    }
  }
  override def update(event: ObserverEvent): Unit = {
    event match {
      case ObserverEvent.StartGame => {
        updateControllerOfScenes()
        stage.setScene(gamePlayerScene)
      }
      case ObserverEvent.PhaseChange => {
        Platform.runLater(() => {
          moveToGamePlayerScene(controller.game.currentPlayerIndex)
          updateControllerOfScenes()
          stage.setScene(gamePlayerScene)
        })
      }
      case ObserverEvent.Plant => {
        Platform.runLater(() => {
          updateControllerOfScenes()
          stage.setScene(gamePlayerScene)
        })
      }
      case ObserverEvent.Harvest => {
        Platform.runLater(() => {
          updateControllerOfScenes()
          stage.setScene(gamePlayerScene)
        })
      }
      case ObserverEvent.Take => {
        Platform.runLater(() => {
          updateControllerOfScenes()
          stage.setScene(gamePlayerScene)
        })
      }
      case ObserverEvent.GameInfo => {
        Platform.runLater(() => {
          updateControllerOfScenes()
          stage.setScene(gamePlayerScene)
        })
      }
      case ObserverEvent.Draw => {
        Platform.runLater(() => {
          updateControllerOfScenes()
          stage.setScene(gamePlayerScene)
        })
      }
      case ObserverEvent.Turn => {
        Platform.runLater(() => {
          updateControllerOfScenes()
          stage.setScene(gamePlayerScene)
        })
      }
      case ObserverEvent.Undo => {
        Platform.runLater(() => {
          updateControllerOfScenes()
          stage.setScene(gamePlayerScene)
        })
      }
      case ObserverEvent.Redo => {
        Platform.runLater(() => {
          updateControllerOfScenes()
          stage.setScene(gamePlayerScene)
        })
      }
      case ObserverEvent.CreatePlayer =>
    }
  }

  def updateControllerOfScenes(): Unit = {
    gamePlayerScene = gamePlayerScene.copy(
      controller = controller,
      currentPlayerViewIndex = currentPlayerViewIndex
    )
    gameInfoScene = gameInfoScene.copy(
      controller = controller
    )
  }

  def moveToGamePlayerScene(index: Int): Unit = {
    currentPlayerViewIndex = index
    updateControllerOfScenes()
    stage.setScene(gamePlayerScene)
  }
}

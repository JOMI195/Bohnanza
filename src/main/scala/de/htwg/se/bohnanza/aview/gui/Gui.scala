package bohnanza.aview.gui

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.text.Font
import bohnanza.aview.gui.components.*
import bohnanza.util.Observer
import bohnanza.controller.Controller
import bohnanza.util.ObserverEvent
import bohnanza.model.HandlerResponse
import scalafx.application.JFXApp3.PrimaryStage
import bohnanza.aview.gui.scenes.GamePlayerScene
import bohnanza.aview.gui.scenes.StartScene
import bohnanza.aview.gui.scenes.PlayerCreateScene
import bohnanza.aview.gui.scenes.GameInfoScene

object Styles {
  val baseCss = getClass.getResource("/styles/base.css").toExternalForm
  val startCss =
    getClass.getResource("/styles/start.css").toExternalForm
  val playerCreationCss =
    getClass.getResource("/styles/playerCreation.css").toExternalForm
  val gameCss = getClass.getResource("/styles/game.css").toExternalForm
}

class Gui(controller: Controller) extends JFXApp3 with Observer {

  var startScene: StartScene = _

  var playerCreationScene: PlayerCreateScene = _

  var gamePlayerScene: GamePlayerScene = _

  var gameInfoScene: GameInfoScene = _

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

    gamePlayerScene = GamePlayerScene(
      controller = controller,
      windowWidth = windowWidth,
      windowHeight = windowHeight,
      onGameInfoButtonClick = () => stage.setScene(gameInfoScene)
    )

    gameInfoScene = GameInfoScene(
      controller = controller,
      windowWidth = windowWidth,
      windowHeight = windowHeight,
      onGoBackToGameButtonClick = () => stage.setScene(gamePlayerScene)
    )

    // Primary Stage
    stage = new JFXApp3.PrimaryStage {
      resizable = true
      icons += new Image(getClass().getResourceAsStream("/images/gameIcon.png"))
      title = "Bohnanza"
      scene = startScene
    }
  }

  // ------------ Observer ---------------------------------------------------
  override def update(error: HandlerResponse): Unit = {
    error match {
      case HandlerResponse.BeanFieldIndexError        =>
      case HandlerResponse.PlayerIndexError           =>
      case HandlerResponse.CurrentPlayerIndexError    =>
      case HandlerResponse.TurnOverFieldIndexError    =>
      case HandlerResponse.HandIndexError             =>
      case HandlerResponse.MethodError                =>
      case HandlerResponse.ArgsError                  =>
      case HandlerResponse.Success                    =>
      case HandlerResponse.MissingPlayerCreationError =>
      case HandlerResponse.TakeInvalidPlantError      =>
      case HandlerResponse.InvalidPlantError          =>
    }
  }
  override def update(event: ObserverEvent): Unit = {
    event match {
      case ObserverEvent.StartGame => {
        gamePlayerScene = gamePlayerScene.copy(controller = controller)
        gameInfoScene = gameInfoScene.copy(controller = controller)
        stage.setScene(gamePlayerScene)
      }
      case ObserverEvent.PhaseChange  =>
      case ObserverEvent.Plant        =>
      case ObserverEvent.Harvest      =>
      case ObserverEvent.Take         =>
      case ObserverEvent.GameInfo     =>
      case ObserverEvent.Draw         =>
      case ObserverEvent.Turn         =>
      case ObserverEvent.Undo         =>
      case ObserverEvent.Redo         =>
      case ObserverEvent.CreatePlayer => println("gui: playerCreated")
    }
  }
}

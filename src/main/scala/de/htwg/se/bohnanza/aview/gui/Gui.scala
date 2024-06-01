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

class Gui(controller: Controller) extends JFXApp3 with Observer {

  lazy val playerCreationScene: Scene = new Scene(windowWidth, windowHeight) {
    root = new VBox(0) {
      fillWidth = false
      alignment = Pos.CENTER
      children = Seq(
        CreatePlayerCard(controller)
      )
    }
  }

  lazy val startScene: Scene = new Scene(windowWidth, windowHeight) {
    val resourceImagesUrl = "/images/start/"

    /** BUTTON */
    val playButton = GameButtonFactory.createGameButton(
      text = "Play",
      width = 350,
      height = 70
    ) { () =>
      stage.setScene(playerCreationScene)
    }

    val playButtonBox = new VBox(0) {
      alignment = Pos.BOTTOM_CENTER
      children = Seq(playButton)
    }
    playButtonBox.prefHeight = 180

    /** IMAGE */
    val titleImage = importImage(resourceImagesUrl + "title.png", 0.6)
    val titleBox = new VBox(0) {
      alignment = Pos.BOTTOM_CENTER
      children = Seq(titleImage)
    }
    titleBox.prefHeight = windowHeight / 2

    root = new VBox(0) {
      alignment = Pos.TOP_CENTER
      children = Seq(
        titleBox,
        playButtonBox
      )
    }
  }

  lazy val gamePlayerScene: Scene = new Scene(windowWidth, windowHeight) {
    val gameLabel = new Label("This is the Game View")
    val backButton = new Button("Back to Start")

    root = new VBox(20) {
      children = Seq(
        gameLabel,
        backButton
      )
    }

    backButton.onAction = () => stage.setScene(startScene)
  }

  controller.add(this)

  // ------------ Variables ---------------------------------------------------
  private val windowWidth = 1366
  private val windowHeight = 768

  // ------------ Load CSS and Fonts ------------------------------------------
  Font.loadFont(getClass.getResourceAsStream("/fonts/HoltwoodOneSC.ttf"), 50)
  private val baseCss = getClass.getResource("/styles/base.css").toExternalForm
  private val startCss =
    getClass.getResource("/styles/start.css").toExternalForm
  private val playerCreationCss =
    getClass.getResource("/styles/playerCreation.css").toExternalForm
  private val gameCss = getClass.getResource("/styles/game.css").toExternalForm

  // ------------ Funtions ----------------------------------------------------
  def importImage(imageUrl: String, scaleFactor: Float): ImageView = {
    val image = new Image(
      getClass().getResourceAsStream(imageUrl)
    )
    val imageView = new ImageView(image)
    imageView.fitWidth = image.width.value * scaleFactor
    imageView.fitHeight = image.height.value * scaleFactor
    imageView
  }

  // ------------ Gui --------------------------------------------------------
  /** Defines and draws the layout of the Game GUI */
  override def start(): Unit = {
    // start View
    startScene.getStylesheets.add(baseCss)
    startScene.getStylesheets.add(startCss)

    // Player Creation View

    playerCreationScene.getStylesheets.add(baseCss)
    playerCreationScene.getStylesheets.add(playerCreationCss)

    // Game Player View

    gamePlayerScene.getStylesheets.add(baseCss)
    gamePlayerScene.getStylesheets.add(gameCss)

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
      case ObserverEvent.StartGame    => stage.setScene(gamePlayerScene)
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

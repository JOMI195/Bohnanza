import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.text.Font
import bohnanza.aview.gui.*

object Gui extends JFXApp3 {

  // ------------ Variables ------------
  private val windowWidth = 1366
  private val windowHeight = 768

  // ------------ Load CSS and Fonts ------------
  Font.loadFont(getClass.getResourceAsStream("/fonts/HoltwoodOneSC.ttf"), 50)
  private val baseCss = getClass.getResource("/styles/base.css").toExternalForm
  private val startCss =
    getClass.getResource("/styles/start.css").toExternalForm
  private val playerCreationCss =
    getClass.getResource("/styles/playerCreation.css").toExternalForm
  private val gameCss = getClass.getResource("/styles/game.css").toExternalForm

  // ------------ Funtions ------------
  def importImage(imageUrl: String, scaleFactor: Float): ImageView = {
    val image = new Image(
      getClass().getResourceAsStream(imageUrl)
    )
    val imageView = new ImageView(image)
    imageView.fitWidth = image.width.value * scaleFactor
    imageView.fitHeight = image.height.value * scaleFactor
    imageView
  }

  // ------------ Gui ------------
  /** Defines and draws the layout of the Game GUI */
  override def start(): Unit = {
    // start View
    lazy val startView: Scene = new Scene(windowWidth, windowHeight) {
      val resourceImagesUrl = "/images/start/"

      /** BUTTON */
      val startButton = GameButtonFactory.createGameButton(
        text = "Play",
        width = 350,
        height = 70
      ) {
        stage.setScene(playerCreationView)
      }

      val startButtonBox = new VBox(0) {
        alignment = Pos.BOTTOM_CENTER
        children = Seq(startButton)
      }
      startButtonBox.prefHeight = 180

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
          startButtonBox
        )
      }
    }
    startView.getStylesheets.add(baseCss)
    startView.getStylesheets.add(startCss)

    // Player Creation View
    lazy val playerCreationView: Scene = new Scene(windowWidth, windowHeight) {
      val startButton = GameButtonFactory.createGameButton(
        text = "Start",
        width = 350,
        height = 70
      ) {
        stage.setScene(gamePlayerView)
      }

      val startButtonBox = new VBox(0) {
        alignment = Pos.TOP_CENTER
        children = Seq(startButton)
      }
      startButtonBox.prefHeight = 180

      root = new VBox(0) {
        alignment = Pos.BOTTOM_CENTER
        children = Seq(
          startButtonBox
        )
      }
    }
    playerCreationView.getStylesheets.add(baseCss)
    playerCreationView.getStylesheets.add(playerCreationCss)

    // Game Olayer View
    lazy val gamePlayerView: Scene = new Scene(windowWidth, windowHeight) {
      val gameLabel = new Label("This is the Game View")
      val backButton = new Button("Back to Start")

      root = new VBox(20) {
        children = Seq(
          gameLabel,
          backButton
        )
      }

      backButton.onAction = () => stage.setScene(startView)
    }
    gamePlayerView.getStylesheets.add(baseCss)
    gamePlayerView.getStylesheets.add(gameCss)

    // Primary Stage
    stage = new JFXApp3.PrimaryStage {
      resizable = false
      icons += new Image(getClass().getResourceAsStream("/images/gameIcon.png"))
      title = "Bohnanza"
      scene = startView
    }
  }
}

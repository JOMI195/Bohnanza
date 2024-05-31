package de.htwg.se.bohnanza

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Text
import scalafx.scene.image.{Image, ImageView}

object ScalaFXGameStartScreen extends JFXApp3 {
  override def start(): Unit = {

    // Load background image
    val backgroundImage = new Image(
      getClass.getResourceAsStream("/background.jpeg")
    )
    val backgroundImageView = new ImageView(backgroundImage)

    // Load CSS file
    /* val cssFile = getClass
      .getResource(
        "C://Users//johan//Documents//GitHub//Bohnanza//src//main//scala//de//htwg//se//bohnanza//styles.css"
      )
      .toExternalForm */

    // Create start screen
    val startButton = new Button("Start")
    startButton.getStyleClass.add("start-button")
    val infoButton = new Button("Info")
    infoButton.getStyleClass.add("info-button")
    val startText = new Text("Welcome to the Game!")

    val startLayout = new HBox(10)
    startLayout.padding = Insets(50, 80, 50, 80)
    startLayout.children.addAll(startText, startButton, infoButton)

    val startScreen = new BorderPane()
    startScreen.center = startLayout
    /* startScreen.style =
      "-fx-background-image: url('" + backgroundImage + "'); -fx-background-size: cover;"
     */
    // Apply CSS to the scene
    // startScreen.getStylesheets.add(cssFile)

    // Create game screen
    val gameText = new Text("Game Screen")
    val backButtonGame = new Button("Back to Start")
    val gameLayout = new BorderPane()
    gameLayout.center = gameText
    gameLayout.bottom = backButtonGame

    backButtonGame.onAction = _ =>
      stage.scene = new Scene {
        fill = Color.White
        content = startScreen
      }

    // Create instructions screen
    val instructionsText = new Text("Game Instructions")
    val backButtonInstructions = new Button("Back to Start")
    val instructionsLayout = new BorderPane()
    instructionsLayout.center = instructionsText
    instructionsLayout.bottom = backButtonInstructions

    backButtonInstructions.onAction = _ =>
      stage.scene = new Scene {
        content = startScreen
      }

    startButton.onAction = _ =>
      stage.scene = new Scene {
        content = gameLayout
      }

    infoButton.onAction = _ =>
      stage.scene = new Scene {
        content = instructionsLayout
      }

    // Create primary stage and set the start screen
    stage = new JFXApp3.PrimaryStage {
      title = "ScalaFX Game Start Screen"
      width = 900
      height = 600
      scene = new Scene(startScreen)
    }
  }
}

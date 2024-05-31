package de.htwg.se.bohnanza

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Text

object Guitest extends JFXApp3 {
  override def start(): Unit = {

    // Load CSS file
    val cssFile = getClass.getResource("/styles.css").toExternalForm

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

    // Apply CSS to the scene
    startScreen.getStylesheets.add(cssFile)

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
        stylesheets.add(cssFile)
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
        stylesheets.add(cssFile)
      }

    startButton.onAction = _ =>
      stage.scene = new Scene {
        content = gameLayout
        stylesheets.add(cssFile)
      }

    infoButton.onAction = _ =>
      stage.scene = new Scene {
        content = instructionsLayout
        stylesheets.add(cssFile)
      }

    // Create primary stage and set the start screen
    stage = new JFXApp3.PrimaryStage {
      title = "Bohnanza"
      width = 900
      height = 600
      scene = new Scene(startScreen) {
        stylesheets.add(cssFile)
      }
    }
  }
}

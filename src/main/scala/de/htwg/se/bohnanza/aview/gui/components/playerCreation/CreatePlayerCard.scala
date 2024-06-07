package bohnanza.aview.gui.components.playerCreation

import scalafx.scene.layout.VBox
import scalafx.scene.text.{Font, Text}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.paint.Color
import scalafx.scene.control.Button
import scalafx.event.ActionEvent
import bohnanza.controller.Controller
import bohnanza.aview.gui.components.global.GameButtonFactory

class CreatePlayerCard(controller: Controller) extends VBox {
  prefHeight = 450
  prefWidth = 450
  fillWidth = false
  padding = Insets(top = 10, right = 20, bottom = 10, left = 20)
  alignment = Pos.Center

  style = "-fx-background-color: #FFCD92;" +
    "-fx-background-radius: 36;" +
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5);"
  val createPlayersTitle = new Text {
    text = "Create Players"
    style = "-fx-fill: #7A2626; -fx-font-size: 30;"
  }

  val nameTitle = new Text {
    text = "Name"
    alignmentInParent = Pos.BASELINE_LEFT
  }

  children.addAll(createPlayersTitle, nameTitle)

  val maxAllowedPlayersCount = 4
  val playerTextInputFields =
    for (_ <- 1 to maxAllowedPlayersCount) yield PlayerTextInputField()
  val playerTextInputFieldVBox =
    new VBox(8) {
      children = playerTextInputFields
    }

  playerTextInputFieldVBox.padding =
    Insets(top = 0, right = 20, bottom = 20, left = 20)

  val startButton = GameButtonFactory.createGameButton(
    text = "Start",
    width = 350,
    height = 70
  )(onFinishedAction = () => {
    getPlayerNames().foreach(controller.createPlayer)
    controller.nextPhase
  })

  val startButtonBox = new VBox(0) {
    alignment = Pos.TOP_CENTER
    children = Seq(startButton)
  }

  children.addAll(playerTextInputFieldVBox, startButtonBox)

  def getPlayerNames(): Seq[String] = {
    playerTextInputFields.map(_.text.value).filter(!_.isEmpty())
  }
}

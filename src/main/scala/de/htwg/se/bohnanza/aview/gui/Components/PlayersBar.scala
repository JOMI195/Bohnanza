package bohnanza.aview.gui.components

import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.shape.Circle
import scalafx.geometry.Insets
import scalafx.scene.paint.Color
import bohnanza.model.Player
import scalafx.geometry.Pos

def getRandomAvatar(): Image = {
  val directory = new File("images/game/")
  val files = directory.listFiles()
  val randomIndex = Random.nextInt(files.length)
  new Image(files(randomIndex).toURI.toString)
}

class PlayersBar(players: List[Player]) extends HBox {

  padding = Insets(10)
  spacing = 10

  val playersDummy = List(Player("JOMI"), Player("Daniel"))
  playersDummy.foreach { player =>
    val avatar = new Circle {
      radius = 50
      fill = Color.Blue
    }
    val changePlayerGameButton = GameButtonFactory.createGameButton(
      text = player.name,
      width = 100,
      height = 10
    ) { () => }
    changePlayerGameButton.style = "-fx-font-size: 10"

    val playerBox = new VBox {
      alignment = Pos.TOP_CENTER
      padding = Insets(5)
      spacing = 5
      children = Seq(
        avatar,
        changePlayerGameButton
      )
    }

    children.add(playerBox)
  }
}

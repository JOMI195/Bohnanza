package bohnanza.aview.gui.components.gamePlayer

import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.control.Label
import scalafx.geometry.Pos
import bohnanza.model.Player
import bohnanza.aview.gui.components.global.GameButtonFactory
import de.htwg.se.bohnanza.controller.ControllerComponent.*
import scalafx.scene.text.Text
import bohnanza.aview.gui.components.global.PlayerAvatar
import bohnanza.aview.gui.components.global.GameLabel
import scalafx.geometry.Insets

class Actions(
    controller: IController,
    onPlantButtonClick: () => Unit,
    onHarvestButtonClick: () => Unit,
    onDrawButtonClick: () => Unit
) extends VBox {
  val buttonWidth = 180
  val buttonHeight = 35
  val buttonFontsize = 16
  val buttonSpacing = 5

  val topButtons = new HBox {
    val plantButton = GameButtonFactory.createGameButton(
      text = "Plant",
      width = buttonWidth,
      height = buttonHeight
    ) { () =>
      onPlantButtonClick()
    }
    plantButton.style = s"-fx-font-size: ${buttonFontsize}"

    val harvestButton = GameButtonFactory.createGameButton(
      text = "Harvest",
      width = buttonWidth,
      height = buttonHeight
    ) { () =>
      onHarvestButtonClick()
    }
    harvestButton.style = s"-fx-font-size: ${buttonFontsize}"

    spacing = buttonSpacing
    children.addAll(plantButton, harvestButton)
  }

  val midButtons = new HBox {
    val phaseChangeButton = GameButtonFactory.createGameButton(
      text = "Next Phase",
      width = buttonWidth,
      height = buttonHeight
    ) { () =>
      controller.nextPhase
    }
    phaseChangeButton.style = s"-fx-font-size: ${buttonFontsize}"

    val drawButton = GameButtonFactory.createGameButton(
      text = "Draw",
      width = buttonWidth,
      height = buttonHeight
    ) { () =>
      onDrawButtonClick()
    }
    drawButton.style = s"-fx-font-size: ${buttonFontsize}"

    spacing = buttonSpacing
    children.addAll(phaseChangeButton, drawButton)
  }

  val bottomButtons = new HBox {
    val undoButton = GameButtonFactory.createGameButton(
      text = "Undo",
      width = buttonWidth,
      height = buttonHeight
    ) { () =>
      controller.undo()
    }
    undoButton.style = s"-fx-font-size: ${buttonFontsize}"

    val redoButton = GameButtonFactory.createGameButton(
      text = "Redo",
      width = buttonWidth,
      height = buttonHeight
    ) { () =>
      controller.redo()
    }
    redoButton.style = s"-fx-font-size: ${buttonFontsize}"

    spacing = buttonSpacing
    children.addAll(undoButton, redoButton)
  }

  spacing = buttonSpacing
  children.addAll(topButtons, midButtons, bottomButtons)
}

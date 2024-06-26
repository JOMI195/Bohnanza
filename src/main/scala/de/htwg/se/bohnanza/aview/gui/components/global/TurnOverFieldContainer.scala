package de.htwg.se.bohnanza.aview.gui.components.global

import scalafx.scene.layout.HBox
import scalafx.scene.layout.StackPane
import scalafx.geometry.Pos

import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.aview.gui.utils.ImageUtils
import de.htwg.se.bohnanza.aview.gui.model.SelectionManager
import de.htwg.se.bohnanza.aview.gui.components.gamePlayer.PlayerHand

class TurnOverFieldContainer(
    cards: List[Bean],
    scaleFactor: Float = mainCardScaleFactor
) extends HBox(10) {
  fillHeight = false

  val turnOverFieldCardPath = "/images/cards/"
  val turnOverFieldImage1 = ImageUtils.importImageAsView(
    imageUrl = turnOverFieldCardPath + "turnoverField.png",
    scaleFactor = scaleFactor
  )
  turnOverFieldImage1.style =
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5);"
  val turnOverFieldImage2 = ImageUtils.importImageAsView(
    imageUrl = turnOverFieldCardPath + "turnoverField.png",
    scaleFactor = scaleFactor
  )
  turnOverFieldImage2.style =
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5);"

  val stackPane1 = new StackPane {
    children.add(turnOverFieldImage1)
  }
  val stackPane2 = new StackPane {
    children.add(turnOverFieldImage2)
  }

  var card1: TurnOverFieldCard = _
  var card2: TurnOverFieldCard = _
  if (cards.nonEmpty) {
    card1 = TurnOverFieldCard(
      bean = cards(0),
      selectionManager = None,
      turnOverFieldCardIndex = 0
    )

    stackPane1.children.add(card1)
    card1.translateY = 50

    if (cards.length > 1) {
      card1 = TurnOverFieldCard(
        bean = cards(1),
        selectionManager = None,
        turnOverFieldCardIndex = 1
      )

      card2.translateY = 50
      stackPane2.children.add(card2)
    }
  }

  children.addAll(stackPane1, stackPane2)

  def updateSelectionManager(selectionManager: SelectionManager): Unit = {
    card1.selectionManager = Some(selectionManager)
    card2.selectionManager = Some(selectionManager)
  }
}

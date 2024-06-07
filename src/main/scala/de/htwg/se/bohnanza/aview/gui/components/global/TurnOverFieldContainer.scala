package bohnanza.aview.gui.components.global

import scalafx.scene.layout.HBox
import scalafx.scene.layout.StackPane
import scalafx.geometry.Pos
import bohnanza.model.Bean
import bohnanza.aview.gui.utils.ImageUtils
import bohnanza.aview.gui.model.SelectionManager
import bohnanza.aview.gui.components.gamePlayer.PlayerHand

class TurnOverFieldContainer(
    cards: List[Bean],
    scaleFactor: Float = mainCardScaleFactor,
    selectionManager: Option[SelectionManager],
    playerHand: Option[PlayerHand]
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

  var card1: Card = _
  var card2: Card = _
  if (cards.nonEmpty) {
    card1 = Card(
      bean = cards(0),
      scaleFactor = scaleFactor,
      selectionManager = selectionManager,
      selectable = true,
      turnOverFieldCardIndex = 0,
      selectedCards = playerHand match {
        case None => List.empty
        case Some(checkedPlayerHand) =>
          List(checkedPlayerHand.selectableCard)
      }
    )

    stackPane1.children.add(card1)
    card1.translateY = 50

    if (cards.length > 1) {
      card2 = Card(
        bean = cards(1),
        scaleFactor = scaleFactor,
        selectionManager = selectionManager,
        selectable = true,
        turnOverFieldCardIndex = 1,
        selectedCards = playerHand match {
          case None => List.empty
          case Some(checkedPlayerHand) =>
            List(checkedPlayerHand.selectableCard)
        }
      )

      card1.selectedCards = card2 :: card1.selectedCards
      card2.selectedCards = card1 :: card2.selectedCards
      card2.translateY = 50
      stackPane2.children.add(card2)
    }
  }

  children.addAll(stackPane1, stackPane2)

  def deselect() = {
    if (cards.nonEmpty) {
      card1.deselect()
      if (cards.length > 1)
        card2.deselect()
    }

  }
}

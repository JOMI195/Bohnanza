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

  var turnOverFieldCards: List[Card] = List.empty
  if (cards.nonEmpty && cards.length > 1) {
    turnOverFieldCards = Card(
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
    ) :: turnOverFieldCards

    turnOverFieldCards = Card(
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
    ) :: turnOverFieldCards

    println(turnOverFieldCards)

    turnOverFieldCards(0).selectedCards =
      turnOverFieldCards(1) :: turnOverFieldCards(0).selectedCards
    turnOverFieldCards(1).selectedCards =
      turnOverFieldCards(0) :: turnOverFieldCards(1).selectedCards

    turnOverFieldCards(0).translateY = 50
    turnOverFieldCards(1).translateY = 50
    stackPane1.children.add(turnOverFieldCards(0))
    stackPane2.children.add(turnOverFieldCards(1))
  }

  children.addAll(stackPane1, stackPane2)

  def deselect() = {
    for (card <- turnOverFieldCards) {
      card.deselect()
    }
  }

}

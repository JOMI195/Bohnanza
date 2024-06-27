package de.htwg.se.bohnanza.aview.gui.components.gamePlayer

import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.IPlayer

import de.htwg.se.bohnanza.aview.gui.components.global.Card
import de.htwg.se.bohnanza.aview.gui.components.global.Hand
import de.htwg.se.bohnanza.aview.gui.components.global.GameButtonFactory
import de.htwg.se.bohnanza.aview.gui.model.SelectionManager
import de.htwg.se.bohnanza.aview.gui.components.global.TurnOverFieldContainer

import scalafx.geometry.Pos
import scalafx.scene.layout.VBox
import de.htwg.se.bohnanza.aview.gui.components.global.HandCard

case class PlayerHand(currentViewPlayer: IPlayer) extends VBox {
  var flipped = true
  var selectableCard: HandCard = _
  val handcards: List[HandCard] = currentViewPlayer.hand.cards match {
    case Nil => List.empty
    case head :: tail =>
      selectableCard = new HandCard(
        bean = head,
        isSelectable = true,
        selectionManager = None
      )
      val otherCards = tail.map { bean =>
        new HandCard(
          bean = bean,
          selectionManager = None
        )
      }
      selectableCard :: otherCards
  }
  val hand = Hand(cards = handcards)

  val buttonWidth = 180
  val buttonHeight = 35
  val buttonFontsize = 16
  val buttonSpacing = 5

  val flipCardsButton = GameButtonFactory.createGameButton(
    text = "Flip Hand Cards",
    width = buttonWidth,
    height = buttonHeight
  ) { () =>
    onFlipCardsButtonClick()
  }
  flipCardsButton.style = s"-fx-font-size: ${12}"

  if (currentViewPlayer.hand.cards.isEmpty) { flipCardsButton.visible = false }
  else { flipCardsButton.visible = true }

  children = Seq(flipCardsButton, hand.flippAllCards())
  alignment = Pos.TOP_CENTER
  spacing = buttonSpacing

  def onFlipCardsButtonClick(): Unit = {
    flipped = !flipped

    if (flipped) {
      children = Seq(flipCardsButton, hand.flippAllCards())
    } else {
      children = Seq(flipCardsButton, hand)
    }
  }

  def updateSelectionManager(selectionManager: SelectionManager): Unit = {
    if (!handcards.isEmpty)
      selectableCard.selectionManager = Some(selectionManager)
  }
}

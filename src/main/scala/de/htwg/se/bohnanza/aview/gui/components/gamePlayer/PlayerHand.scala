package bohnanza.aview.gui.components.gamePlayer

import bohnanza.aview.gui.components.global.Card
import bohnanza.aview.gui.components.global.Hand
import scalafx.scene.layout.VBox
import bohnanza.model.Player
import bohnanza.aview.gui.components.global.GameButtonFactory
import scalafx.geometry.Pos

class PlayerHand(currentViewPlayer: Player) extends VBox {
  var flipped = true
  val handcards: List[Card] = currentViewPlayer.hand.cards.map { bean =>
    Card(bean = bean, scaleFactor = 0.4)
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

  def onFlipCardsButtonClick(): Unit = {
    flipped = !flipped

    if (flipped) {
      children = Seq(flipCardsButton, hand.flippAllCards())
    } else {
      children = Seq(flipCardsButton, hand)
    }
  }

  if (currentViewPlayer.hand.cards.isEmpty) { flipCardsButton.visible = false }
  else { flipCardsButton.visible = true }

  children = Seq(flipCardsButton, hand.flippAllCards())
  alignment = Pos.TOP_CENTER
  spacing = buttonSpacing
}

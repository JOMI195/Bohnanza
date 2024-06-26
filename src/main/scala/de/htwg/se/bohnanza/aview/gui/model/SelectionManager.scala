package de.htwg.se.bohnanza.aview.gui.model

import de.htwg.se.bohnanza.aview.gui.components.gamePlayer.PlayerHand
import de.htwg.se.bohnanza.aview.gui.components.global.PlayerBeanFields
import de.htwg.se.bohnanza.aview.gui.components.global.TurnOverFieldContainer
import de.htwg.se.bohnanza.aview.gui.components.global.Cards

val selectionStyle =
  "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5); -fx-border-color: #FEED5D; -fx-border-width: 2; -fx-border-radius: 10px;"

class SelectionManager(
    var selectedBeanFieldIndex: Int = -1,
    var selectFromHand: Boolean = false,
    var selectedTurnOverFieldIndex: Int = -1,
    val playerBeanFields: PlayerBeanFields,
    val playerHand: PlayerHand,
    val turnOverField: TurnOverFieldContainer
) {
  var selectedCards: List[Cards] = List.empty;

  def selectOnClick(
      isHandCard: Boolean,
      turnOverFieldCardIndex: Int
  ): Unit = {
    if (isHandCard) {
      if (playerHand.selectableCard.isSelected) {
        deselect();
      } else {
        selectedCards = playerHand.selectableCard :: selectedCards;
        selectFromHand = true;
      }
    } else if (turnOverFieldCardIndex != -1) {
      if ()
      selectedTurnOverFieldIndex = turnOverFieldCardIndex;
    }

      selectedCards
        .filter(_ != null)
        .foreach { card =>
          if (handCard) {
            card.deselect()
          } else {
            card.deselectTurnOverFieldCards()
          }
        }
    }
    style = if (isSelected) selectionStyle else defaultCardStyle

    if (isSelected) {
      pulsateTransition.play()
    } else {
      pulsateTransition.stop()
      cardImage.scaleX = 1.0
      cardImage.scaleY = 1.0
    }

  }

  def deselect(): Unit = {
    if (selectable) {
      selectionManager match {
        case None =>
        case Some(checkedSelectionManager) => {
          // need to be careful since filter in gamePlayerScene runs this everytime a card is not selected
          isSelected = false
          style = defaultCardStyle
          if (handCard) {
            checkedSelectionManager.selectFromHand = false
          } else {
            checkedSelectionManager.selectedTurnOverFieldIndex = -1
          }
          pulsateTransition.stop()
          cardImage.scaleX = 1.0
          cardImage.scaleY = 1.0
        }
      }

      isSelected = false
      style = defaultCardStyle
      pulsateTransition.stop()
      cardImage.scaleX = 1.0
      cardImage.scaleY = 1.0
    }
  }

  def deselectTurnOverFieldCards(): Unit = {
    isSelected = false
    style = defaultCardStyle
    pulsateTransition.stop()
    cardImage.scaleX = 1.0
    cardImage.scaleY = 1.0
  }
}

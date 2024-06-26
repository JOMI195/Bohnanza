package de.htwg.se.bohnanza.aview.gui.model

import de.htwg.se.bohnanza.aview.gui.components.gamePlayer.PlayerHand
import de.htwg.se.bohnanza.aview.gui.components.global.PlayerBeanFields
import de.htwg.se.bohnanza.aview.gui.components.global.TurnOverFieldContainer
import de.htwg.se.bohnanza.aview.gui.components.global.Cards
import de.htwg.se.bohnanza.aview.gui.components.global.{
  Card,
  TurnOverFieldCard,
  HandCard
}
import scalafx.animation.ScaleTransition

val selectionStyle =
  "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5); -fx-border-color: #FEED5D; -fx-border-width: 2; -fx-border-radius: 10px;"

val defaultCardStyle =
  "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5);"

class SelectionManager(
    var selectedBeanFieldIndex: Int = -1,
    var selectFromHand: Boolean = false,
    var selectedTurnOverFieldIndex: Int = -1,
    val playerBeanFields: PlayerBeanFields,
    val playerHand: PlayerHand,
    val turnOverField: TurnOverFieldContainer
) {

  def selectHandCard(): Unit = {
    val selectableCard = playerHand.selectableCard
    if (selectableCard.isSelected) {
      deselectHandCard()
    } else {
      selectableCard.isSelected = true
      selectableCard.style = selectionStyle
      selectableCard.pulsateTransition.play()
    }
  }

  def deselectHandCard(): Unit = {
    val selectableCard = playerHand.selectableCard
    selectableCard.pulsateTransition.stop()
    selectableCard.cardImage.scaleX = 1.0
    selectableCard.cardImage.scaleY = 1.0
    selectableCard.style = defaultCardStyle
    selectableCard.isSelected = false
  }

  def selectTurnOverFieldCard(turnOverFieldCardIndex: Int): Unit = {
    val (selectedTurnOverFieldCard, otherTurnOverFieldCard) =
      if (turnOverFieldCardIndex == 0)
        (turnOverField.card1, turnOverField.card2)
      else (turnOverField.card2, turnOverField.card1)
    if (selectedTurnOverFieldIndex == turnOverFieldCardIndex) {
      deselectTurnOverFieldCard(selectedTurnOverFieldCard)
      return
    } else if (otherTurnOverFieldCard.isSelected) {
      deselectTurnOverFieldCard(otherTurnOverFieldCard)
    }
    selectedTurnOverFieldCard.isSelected = true
    selectedTurnOverFieldCard.style = selectionStyle
    selectedTurnOverFieldCard.pulsateTransition.play()
  }

  def deselectTurnOverFieldCard(turnOverFieldCard: TurnOverFieldCard): Unit = {
    turnOverFieldCard.pulsateTransition.stop()
    turnOverFieldCard.cardImage.scaleX = 1.0
    turnOverFieldCard.cardImage.scaleY = 1.0
    turnOverFieldCard.style = defaultCardStyle
    turnOverFieldCard.isSelected = false
  }
}

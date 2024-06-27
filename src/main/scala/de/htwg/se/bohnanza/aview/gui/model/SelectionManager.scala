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
import de.htwg.se.bohnanza.aview.gui.components.global.defaultCardStyle
import de.htwg.se.bohnanza.aview.gui.components.global.BeanFieldContainer

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
      if (selectedTurnOverFieldIndex != -1) {
        val selectedTurnOverFieldCard =
          if (selectedTurnOverFieldIndex == 0)
            turnOverField.card1
          else turnOverField.card2
        deselectTurnOverFieldCard(selectedTurnOverFieldCard)
      }
      selectFromHand = true
      selectableCard.isSelected = true
      selectableCard.pulsateTransition.play()
      selectedTurnOverFieldIndex = -1
    }
  }

  def deselectHandCard(): Unit = {
    val selectableCard = playerHand.selectableCard
    if (selectableCard != null) {
      selectableCard.pulsateTransition.stop()
      selectableCard.cardImage.scaleX = 1.0
      selectableCard.cardImage.scaleY = 1.0
      selectableCard.style = defaultCardStyle
      selectableCard.isSelected = false
      selectFromHand = false
    }
  }

  def selectTurnOverFieldCard(turnOverFieldCardIndex: Int): Unit = {
    val (selectedTurnOverFieldCard, otherTurnOverFieldCard) =
      if (turnOverFieldCardIndex == 0)
        (turnOverField.card1, turnOverField.card2)
      else (turnOverField.card2, turnOverField.card1)
    if (selectedTurnOverFieldIndex == turnOverFieldCardIndex) {
      deselectTurnOverFieldCard(selectedTurnOverFieldCard)
      selectedTurnOverFieldIndex = -1
      return
    } else if (
      otherTurnOverFieldCard != null && otherTurnOverFieldCard.isSelected
    ) {
      deselectTurnOverFieldCard(otherTurnOverFieldCard)
    }

    if (selectFromHand) {
      deselectHandCard()
    }
    selectedTurnOverFieldCard.isSelected = true
    selectedTurnOverFieldCard.pulsateTransition.play()
    selectedTurnOverFieldIndex = turnOverFieldCardIndex
  }

  def deselectTurnOverFieldCard(turnOverFieldCard: TurnOverFieldCard): Unit = {
    turnOverFieldCard.pulsateTransition.stop()
    turnOverFieldCard.cardImage.scaleX = 1.0
    turnOverFieldCard.cardImage.scaleY = 1.0
    turnOverFieldCard.style = defaultCardStyle
    turnOverFieldCard.isSelected = false
  }

  def deselectAllOnAction(): Unit = {
    if (turnOverField.cards.nonEmpty) {
      deselectTurnOverFieldCard(turnOverField.card1)
      if (turnOverField.cards.length > 1)
        deselectTurnOverFieldCard(turnOverField.card2)
    }

    deselectHandCard()
    if (selectedBeanFieldIndex != -1) {
      deselectBeanField(playerBeanFields.beanFields(selectedBeanFieldIndex))
    }
    selectedBeanFieldIndex = -1
    selectedTurnOverFieldIndex = -1
  }

  def selectBeanField(beanFieldIndex: Int): Unit = {
    val selectedBeanField = playerBeanFields.beanFields(beanFieldIndex)
    if (selectedBeanFieldIndex == beanFieldIndex) {
      deselectBeanField(selectedBeanField)
      selectedBeanFieldIndex = -1
    } else {
      if (selectedBeanFieldIndex != -1) {
        deselectBeanField(playerBeanFields.beanFields(selectedBeanFieldIndex))
      }
      selectedBeanField.pulsateTransition.play()
      selectedBeanFieldIndex = beanFieldIndex

    }
  }

  def deselectBeanField(beanFieldContainer: BeanFieldContainer): Unit = {
    beanFieldContainer.pulsateTransition.stop()
    beanFieldContainer.beanFieldImage.scaleX = 1.0
    beanFieldContainer.beanFieldImage.scaleY = 1.0
  }
}

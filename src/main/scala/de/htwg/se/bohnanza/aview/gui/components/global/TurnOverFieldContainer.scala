package bohnanza.aview.gui.components.global

import scalafx.scene.layout.HBox
import scalafx.scene.layout.StackPane
import scalafx.geometry.Pos
import bohnanza.model.Bean

class TurnOverFieldContainer(cards: List[Bean]) extends HBox(10) {
  fillHeight = false
  if (!cards.isEmpty) {
    children.addAll(Card(bean = cards(0)), Card(bean = cards(1)))
  }
}

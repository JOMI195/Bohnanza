package de.htwg.se.bohnanza.aview.gui.components.global

import de.htwg.se.bohnanza.aview.gui.model.SelectionManager

import scalafx.scene.layout.HBox
import scalafx.geometry.Pos

class BeanFieldsContainer(
    beanFields: List[BeanFieldContainer]
) extends HBox {
  fillHeight = false
  children = beanFields
  spacing = 10
}

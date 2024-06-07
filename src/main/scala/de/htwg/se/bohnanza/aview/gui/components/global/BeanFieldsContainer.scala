package bohnanza.aview.gui.components.global

import scalafx.scene.layout.HBox
import scalafx.geometry.Pos
import bohnanza.aview.gui.model.SelectionManager

class BeanFieldsContainer(
    beanFields: List[BeanFieldContainer]
) extends HBox {
  fillHeight = false
  children = beanFields
  spacing = 10
}

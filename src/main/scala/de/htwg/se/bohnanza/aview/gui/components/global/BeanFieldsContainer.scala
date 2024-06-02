package bohnanza.aview.gui.components.global

import scalafx.scene.layout.HBox
import scalafx.geometry.Pos

class BeanFieldsContainer(beanFields: List[BeanFieldContainer]) extends HBox {
  fillHeight = false
  children = beanFields
  spacing = 10
}

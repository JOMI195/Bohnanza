package de.htwg.se.bohnanza.aview.gui.components.global

import de.htwg.se.bohnanza.model.GameComponent.PlayerComponent.IPlayer
import de.htwg.se.bohnanza.Bohnanza.controller
import de.htwg.se.bohnanza.aview.gui.components.global.*
import de.htwg.se.bohnanza.aview.gui.model.SelectionManager
import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.{
  IBeanField,
  BeanField
}

import scalafx.scene.layout.{HBox, VBox}
import scalafx.geometry.Pos
import scalafx.scene.layout.Priority

class PlayerBeanFields(
    player: IPlayer,
    playerIndex: Int,
    scaleFactor: Float = mainCardScaleFactor
) extends HBox {
  val defaultBeanFieldStyle =
    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5);"
  val beanFields: List[BeanFieldContainer] =
    (0 to 2).map(createBeanField).toList
  val beanFieldsContainer = BeanFieldsContainer(beanFields)

  children = beanFieldsContainer

  private def createBeanFieldCards(
      beanFieldIndex: Int
  ): BeanFieldCards = {
    val beanField: IBeanField =
      if (controller.game.players.size > 0)
        controller.game.players(playerIndex).beanFields(beanFieldIndex)
      else BeanField(None)
    val beanFieldCards: List[Card] = beanField.bean
      .map(bean =>
        List.fill(beanField.quantity)(
          BeanFieldCard(bean = bean)
        )
      )
      .getOrElse(List.empty)
    BeanFieldCards(cards = beanFieldCards)
  }

  def createBeanField(beanFieldIndex: Int): BeanFieldContainer = {
    val beanFieldCards = createBeanFieldCards(beanFieldIndex)
    BeanFieldContainer(
      beanFieldCards = beanFieldCards,
      beanFieldId = beanFieldIndex + 1, // the id = index + 1
      scaleFactor = scaleFactor,
      selectionManager = None
    )
  }

  def updateSelectionManager(selectionManager: SelectionManager): Unit = {
    beanFields.foreach { beanField =>
      beanField.selectionManager = Some(selectionManager)
    }
  }

}

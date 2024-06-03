package bohnanza.aview.gui.components.global

import scalafx.scene.layout.{HBox, VBox}
import bohnanza.model.Player
import bohnanza.aview.gui.components.global.*
import bohnanza.model.BeanField
import bohnanza.Bohnanza.controller
import scalafx.geometry.Pos
import scalafx.scene.layout.Priority

val mainCardScaleFactor: Float = 0.35

class PlayerBeanFields(
    player: Player,
    playerIndex: Int,
    scaleFactor: Float = mainCardScaleFactor
) extends HBox {

  val beanFields = (0 to 2).map(createBeanField).toList
  val beanFieldsContainer = BeanFieldsContainer(beanFields)

  children = beanFieldsContainer

  private def createBeanFieldCards(
      beanFieldIndex: Int
  ): BeanFieldCards = {
    val beanField: BeanField =
      if (controller.game.players.size > 0)
        controller.game.players(playerIndex).beanFields(beanFieldIndex)
      else BeanField(None)
    val beanFieldCards: List[Card] = beanField.bean
      .map(bean =>
        List.fill(beanField.quantity)(
          Card(bean = bean, scaleFactor = scaleFactor)
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
      scaleFactor = scaleFactor
    )
  }
}

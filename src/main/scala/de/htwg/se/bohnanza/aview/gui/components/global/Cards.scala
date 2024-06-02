package bohnanza.aview.gui.components.global

import scalafx.scene.layout.{HBox, VBox, StackPane}
import scalafx.geometry.Pos

enum Direction {
  case yAxis
  case xAxis
}

trait Cards(
    cards: List[Card],
    cardTranslation: Int,
    direction: Direction
) {

  def createCardContainer() = {
    new StackPane {
      children = cards.reverse.zipWithIndex.map { case (card, index) =>
        direction match {
          case Direction.xAxis =>
            card.translateX = index * cardTranslation
            card
          case Direction.yAxis =>
            card.translateY = index * cardTranslation
            card
        }
      }
      if (direction == Direction.xAxis) {
        translateX = -(cards.length - 1) * cardTranslation / 2
      }
    }
  }

  def flippAllCards(): Cards
}

case class Hand(cards: List[Card])
    extends HBox
    with Cards(
      cards = cards,
      cardTranslation = 40,
      direction = Direction.xAxis
    ) {
  alignment = Pos.CenterRight

  children = createCardContainer()

  override def flippAllCards(): Hand = {
    copy(cards = cards.map(_.flip()))
  }
}

case class Deck(cards: List[Card])
    extends HBox
    with Cards(
      cards = cards,
      cardTranslation = 1,
      direction = Direction.xAxis
    ) {
  alignment = Pos.CenterLeft

  children = createCardContainer()

  override def flippAllCards(): Deck = {
    copy(cards = cards.map(_.flip()))
  }
}

case class BeanFieldCards(cards: List[Card])
    extends HBox
    with Cards(
      cards = cards,
      cardTranslation = 20,
      direction = Direction.yAxis
    ) {
  alignment = Pos.CENTER

  children = createCardContainer()

  override def flippAllCards(): BeanFieldCards = {
    copy(cards = cards.map(_.flip()))
  }
}

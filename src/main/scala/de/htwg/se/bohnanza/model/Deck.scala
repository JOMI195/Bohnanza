package bohnanza.model
import scala.util.Random

trait DeckCreateStragtegyTemplate {
  def createDeck(): Deck = {
    val cards = fill()
    val shuffledCards = shuffle(cards)
    Deck(shuffledCards)
  }

  def fill(): List[Bean]
  def shuffle(cards: List[Bean]): List[Bean] = Random.shuffle(cards)
}

class FullDeckCreateStrategy extends DeckCreateStragtegyTemplate {
  def fill(): List[Bean] = {
    List.fill(Bean.ChiliBean.frequency)(Bean.ChiliBean) ++
      List.fill(Bean.BlueBean.frequency)(Bean.BlueBean) ++
      List.fill(Bean.StinkyBean.frequency)(Bean.StinkyBean) ++
      List.fill(Bean.GreenBean.frequency)(Bean.GreenBean) ++
      List.fill(Bean.SoyBean.frequency)(Bean.SoyBean) ++
      List.fill(Bean.BlackEyedBean.frequency)(Bean.BlackEyedBean) ++
      List.fill(Bean.RedBean.frequency)(Bean.RedBean) ++
      List.fill(Bean.GardenBean.frequency)(Bean.GardenBean)
  }
}

class SingleChiliBeanDeckCreateStrategy extends DeckCreateStragtegyTemplate {
  def fill(): List[Bean] =
    List.fill(Bean.ChiliBean.frequency)(Bean.ChiliBean)
}

case class Deck(cards: List[Bean]) {

  /** Draws a card from the deck. If the deck is empty, no card is drawn.
    */
  def draw(): (Option[Bean], Deck) = {
    val (card, updatedDeck) = cards match {
      case Nil          => (None, this)
      case head :: tail => (Some(head), Deck(tail))
    }
    (card, updatedDeck)
  }
}

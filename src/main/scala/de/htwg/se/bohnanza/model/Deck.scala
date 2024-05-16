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
    List.fill(Bean.Firebean.frequency)(Bean.Firebean) ++
      List.fill(Bean.BlueBean.frequency)(Bean.BlueBean)
  }
}

class SingleFireBeanDeckCreateStrategy extends DeckCreateStragtegyTemplate {
  def fill(): List[Bean] =
    List.fill(Bean.Firebean.frequency)(Bean.Firebean)
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

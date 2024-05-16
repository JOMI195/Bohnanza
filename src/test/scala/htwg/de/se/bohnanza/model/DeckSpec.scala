import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*

class DeckSpec extends AnyWordSpec with Matchers {

  "A Deck" should {
    "draw from a non-empty deck" in {
      val initialCards = List(Bean.Firebean, Bean.Firebean, Bean.Firebean)
      val initialDeck = Deck(initialCards)

      val (drawnCard, updatedDeck) = initialDeck.draw()

      drawnCard should matchPattern { case Some(Bean.Firebean) => }
      updatedDeck should be(Deck(List(Bean.Firebean, Bean.Firebean)))
    }

    "not draw from an empty deck" in {
      val initialDeck = Deck(Nil)

      val (drawnCard, updatedDeck) = initialDeck.draw()

      drawnCard should be(None)
      updatedDeck should be(Deck(Nil))
    }
  }
}

class DeckCreateStragtegyTemplateSpec extends AnyWordSpec with Matchers {
  "A DeckCreateStategyTemplate" should {
    "fill cards with only FireBean cards" in {
      val singleFireBeanDeckCreater = SingleFireBeanDeckCreateStrategy()
      val cards = singleFireBeanDeckCreater.fill()
      val expectedOutput = List.fill(Bean.Firebean.frequency)(Bean.Firebean)
      Bean.Firebean.frequency shouldBe expectedOutput.length
      cards shouldBe expectedOutput
    }

    "fill cards with all cards that are available" in {
      val fullDeckCreator = FullDeckCreateStrategy()
      val cards = fullDeckCreator.fill()
      val expectedOutput = List.fill(Bean.Firebean.frequency)(Bean.Firebean) ++
        List.fill(Bean.BlueBean.frequency)(Bean.BlueBean)
      Bean.Firebean.frequency + Bean.BlueBean.frequency shouldBe expectedOutput.length
      cards shouldBe expectedOutput
    }
  }
}

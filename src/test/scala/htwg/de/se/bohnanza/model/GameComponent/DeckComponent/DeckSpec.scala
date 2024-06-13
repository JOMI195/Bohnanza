import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.*

class DeckSpec extends AnyWordSpec with Matchers {

  "A Deck" should {
    "draw from a non-empty deck" in {
      val initialCards = List(Bean.ChiliBean, Bean.ChiliBean, Bean.ChiliBean)
      val initialDeck = Deck(initialCards)

      val (drawnCard, updatedDeck) = initialDeck.draw()

      drawnCard should matchPattern { case Some(Bean.ChiliBean) => }
      updatedDeck should be(Deck(List(Bean.ChiliBean, Bean.ChiliBean)))
    }

    "not draw from an empty deck" in {
      val initialDeck = Deck(Nil)

      val (drawnCard, updatedDeck) = initialDeck.draw()

      drawnCard should be(None)
      updatedDeck should be(Deck(Nil))
    }

    "copy the deck with overriden parameters" in {
      val newCards = List(Bean.SoyBean, Bean.BlueBean)
      val copiedDeck = initialDeck.copy(newCards)
      copiedDeck.cards shouldBe newCards
      copiedDeck.cards should not be initialDeck.cards
    }

    "copy the deck without specifying parameters" in {
      val copiedDeck = initialDeck.copy()
      copiedDeck.cards shouldBe initialDeck.cards
      copiedDeck should not be theSameInstanceAs(initialDeck)
    }
  }
}

class DeckCreateStragtegyTemplateSpec extends AnyWordSpec with Matchers {
  "A DeckCreateStategyTemplate" should {
    "fill cards with only ChiliBean cards" in {
      val singleChiliBeanDeckCreater = SingleChiliBeanDeckCreateStrategy()
      val cards = singleChiliBeanDeckCreater.fill()
      val expectedOutput = List.fill(Bean.ChiliBean.frequency)(Bean.ChiliBean)
      Bean.ChiliBean.frequency shouldBe expectedOutput.length
      cards shouldBe expectedOutput
    }

    "fill cards with all cards that are available" in {
      val fullDeckCreator = FullDeckCreateStrategy()
      val cards = fullDeckCreator.fill()
      val expectedOutput =
        List.fill(Bean.ChiliBean.frequency)(Bean.ChiliBean) ++
          List.fill(Bean.BlueBean.frequency)(Bean.BlueBean) ++
          List.fill(Bean.StinkyBean.frequency)(Bean.StinkyBean) ++
          List.fill(Bean.GreenBean.frequency)(Bean.GreenBean) ++
          List.fill(Bean.SoyBean.frequency)(Bean.SoyBean) ++
          List.fill(Bean.BlackEyedBean.frequency)(Bean.BlackEyedBean) ++
          List.fill(Bean.RedBean.frequency)(Bean.RedBean) ++
          List.fill(Bean.GardenBean.frequency)(Bean.GardenBean)
      Bean.ChiliBean.frequency +
        Bean.BlueBean.frequency +
        Bean.StinkyBean.frequency +
        Bean.GreenBean.frequency +
        Bean.SoyBean.frequency +
        Bean.BlackEyedBean.frequency +
        Bean.RedBean.frequency +
        Bean.GardenBean.frequency shouldBe expectedOutput.length
      cards shouldBe expectedOutput
    }
  }
}

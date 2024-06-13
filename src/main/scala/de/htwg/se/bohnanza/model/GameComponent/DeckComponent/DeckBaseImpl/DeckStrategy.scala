package de.htwg.se.bohnanza.model.GameComponent.DeckComponent

import scala.util.Random
import de.htwg.se.bohnanza.model.GameComponent.Bean
import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.{IDeck}

trait IDeckCreateStragtegyTemplate {
  def createDeck(): IDeck = {
    val cards = fill()
    val shuffledCards = shuffle(cards)
    Deck(shuffledCards)
  }

  def fill(): List[Bean]
  def shuffle(cards: List[Bean]): List[Bean] = Random.shuffle(cards)
}

class FullDeckCreateStrategy extends IDeckCreateStragtegyTemplate {
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

class SingleChiliBeanDeckCreateStrategy extends IDeckCreateStragtegyTemplate {
  def fill(): List[Bean] =
    List.fill(Bean.ChiliBean.frequency)(Bean.ChiliBean)
}

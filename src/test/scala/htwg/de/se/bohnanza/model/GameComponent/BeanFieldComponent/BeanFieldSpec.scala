import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent.BeanField
import de.htwg.se.bohnanza.model.GameComponent.Bean

val correctBeanField = BeanField(Option(Bean.ChiliBean), 4)
val notCorrectBeanField = BeanField(Option(Bean.ChiliBean), 5)
val emptyBeanField = BeanField(None)

class BeanFieldSpec extends AnyWordSpec with Matchers {
  "BeanField" should {

    "have correct string representation" when {
      "the field is empty" in {
        emptyBeanField.toString should be("empty")
      }

      "the field contains beans" in {
        correctBeanField.toString should be("ChiliBean x4")
      }
    }

    "harvest should harvest beanField for the corresponding coins" when {
      "beanField has correct quantity for coins" in {
        val (fireCoins, updatedCorrectBeanField) =
          correctBeanField.harvestField()
        updatedCorrectBeanField.quantity shouldBe 0
        fireCoins shouldBe 1
      }

      "beanField is empty" in {
        val (emptyCoins, updatedEmptyBeanField) = emptyBeanField.harvestField()
        updatedEmptyBeanField.quantity shouldBe 0
        emptyCoins shouldBe 0
      }

      "beanField has not exactly the right quantity" in {
        val (fireCoins, updatedUncorrectBeanField) =
          notCorrectBeanField.harvestField()
        updatedUncorrectBeanField.quantity shouldBe 0
        fireCoins shouldBe 1
      }
    }

    "plantToField should plant a bean to a beanField" when {
      "bean is right type" in {
        val updatedEmptyField =
          emptyBeanField.plantToField(Bean.ChiliBean)
        updatedEmptyField.quantity shouldBe emptyBeanField.quantity + 1
        updatedEmptyField.bean shouldBe Some(Bean.ChiliBean)
      }

      "bean is wrong type" in {
        val updatedBeanField =
          correctBeanField.plantToField(Bean.BlueBean)
        updatedBeanField shouldBe correctBeanField
      }
    }
    "copy the BeanField with the specified beans and quantity" in {
      val newBean = Some(Bean.SoyBean)
      val newQuantity = 2
      val copiedBeanField = correctBeanField.copy(newBean, newQuantity)
      copiedBeanField.bean shouldBe newBean
      copiedBeanField.quantity shouldBe newQuantity
      copiedBeanField should not be theSameInstanceAs(correctBeanField)
    }

    "copy the BeanField without specifying new beans and quantity" in {
      val copiedBeanField = correctBeanField.copy()
      copiedBeanField.bean shouldBe correctBeanField.bean
      copiedBeanField.quantity shouldBe correctBeanField.quantity
      copiedBeanField should not be theSameInstanceAs(correctBeanField)
    }
  }
}

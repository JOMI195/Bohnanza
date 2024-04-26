import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*

val correctBeanField = BeanField(Option(Bean.Firebean), 4)
val notCorrectBeanField = BeanField(Option(Bean.Firebean), 5)
val emptyBeanField = BeanField(None)

class BeanFieldSpec extends AnyWordSpec with Matchers {
  "BeanField" should {
    "harvest should harvest beanField for the corresponding coins" when {
      "beanField has correct quantity for coins" in {
        val (fireCoins, updatedCorrectBeanField) =
          correctBeanField.harvestField()
        updatedCorrectBeanField.quantity shouldBe 0
        fireCoins shouldBe 2
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
        fireCoins shouldBe 2
      }
    }

    "plantToField should plant a bean to a beanField" when {
      "bean is right type" in {
        val updatedEmptyField =
          emptyBeanField.plantToField(Bean.Firebean)
        updatedEmptyField.quantity shouldBe emptyBeanField.quantity + 1
        updatedEmptyField.bean shouldBe Some(Bean.Firebean)
      }

      "bean is wrong type" in {
        val updatedBeanField =
          correctBeanField.plantToField(Bean.BlueBean)
        updatedBeanField shouldBe correctBeanField
      }
    }

  }
}

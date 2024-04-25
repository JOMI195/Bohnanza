import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.model.*

val fireBeanField = BeanField(Option(Bean.Firebean), 4)
val emptyBeanField = BeanField(None)

class BeanFieldSpec extends AnyWordSpec with Matchers {
  "BeanField" should {
    "harvest should harvest beanField for the corresponding coins" in {
      val (fireCoins, updatedFireBeanField) = fireBeanField.harvestField()
      val (emptyCoins, updatedEmptyBeanField) = emptyBeanField.harvestField()
      fireCoins shouldBe 2
      emptyCoins shouldBe 0
    }

    "plantToField should plant a bean to a beanField" in {
      val updatedEmptyField =
        emptyBeanField.plantToField(Bean.Firebean)
      updatedEmptyField.quantity shouldBe emptyBeanField.quantity + 1
    }
  }
}

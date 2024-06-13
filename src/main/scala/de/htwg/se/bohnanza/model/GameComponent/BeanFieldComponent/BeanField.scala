package de.htwg.se.bohnanza.model.GameComponent.BeanFieldComponent

import de.htwg.se.bohnanza.model.GameComponent.Bean

case class BeanField(bean: Option[Bean], quantity: Int = 0)
    extends IBeanField(bean, quantity) {

  override def toString: String = {
    bean match {
      case Some(bean) => s"${bean} x$quantity"
      case None       => "empty"
    }

  }

  def harvestField(): (Int, IBeanField) = {
    bean match {
      case Some(bean) => {
        val coins = bean.quantityToCoins.getOrElse(
          quantity, {
            val quantityKey = bean.quantityToCoins.keys
              .filter(_ < quantity)
              .toList
              .sorted
              .lastOption
            quantityKey.map(key => bean.quantityToCoins(key)).getOrElse(0)
          }
        )
        (coins, BeanField(None))
      }
      case None => (0, this)
    }
  }

  def plantToField(beanToPlant: Bean): IBeanField = {
    val updatedBeanfield = bean match {
      case Some(bean) => {
        if (bean == beanToPlant) {
          BeanField(Option(bean), quantity + 1)
        } else {
          this
        }
      }
      case None => BeanField(Option(beanToPlant), 1)
    }
    updatedBeanfield
  }

  def copy(
      bean: Option[Bean] = this.bean,
      quantity: Int = this.quantity
  ): IBeanField = BeanField(bean, quantity)

}

package bohnanza.model

case class BeanField(bean: Option[Bean], quantity: Int = 0) {

  def harvestField(): (Int, BeanField) = {
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

  def plantToField(beanToPlant: Bean): BeanField = {
    println("Field before plant: " + bean + " - Quantity: " + quantity)
    val updatedBeanfield = bean match {
      case Some(bean) => {
        if (bean == beanToPlant) {
          BeanField(Option(bean), quantity + 1)
        } else {
          println(s"Can't add $beanToPlant to $bean field.")
          this
        }
      }
      case None => BeanField(Option(beanToPlant), 1)
    }
    println(
      "Field after plant: " + updatedBeanfield.bean + " - Quantity: " + updatedBeanfield.quantity
    )
    updatedBeanfield
  }
  // def isFieldEmpty(): Boolean {}
  // def isBeanTypeValid(): Boolean {}
}

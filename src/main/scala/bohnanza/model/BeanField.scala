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
  // def plantBeans(bean: Bean, count: Int): BeanField {}
  // def isFieldEmpty(): Boolean {}
  // def isBeanTypeValid(): Boolean {}
}

@main def hello(): Unit =
  val redBean = Card("Red Bean", List(Map("cards" -> 3, "coins" -> 1), Map("cards" -> 5, "coins" -> 2)));
    val blueBean = Card("Blue Bean", List(Map("cards" -> 3, "coins" -> 1), Map("cards" -> 5, "coins" -> 2)))
    println(redBean.getCardValueInfo)

    println("\n")

    val redBeanField = BeanField(redBean, 1)
    val blueBeanField = BeanField(blueBean, 3)

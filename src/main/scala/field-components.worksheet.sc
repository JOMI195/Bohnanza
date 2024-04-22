class Card(val name: String, val valueInfo: List[Map[String, Int]]) {

    override def toString: String = name

    def getCardValueInfo: String = {
        val intro = s"${name} card trading infos"
        val line = "-" * intro.length + "\n" 
        val summary = valueInfo.map { value =>
            s"For x${value("cards")} beans, you get x${value("coins")} coins"
        }
        val infoSummary = intro + "\n" + line + summary.mkString("\n")
        infoSummary
    }
}

val redBean = Card("Red Bean", List(Map("cards" -> 3, "coins" -> 1), Map("cards" -> 5, "coins" -> 2)));
val blueBean = Card("Blue Bean", List(Map("cards" -> 3, "coins" -> 1), Map("cards" -> 5, "coins" -> 2)))
redBean.getCardValueInfo
redBean.name


class BeanField(val card: Card, val quantity: Int) {
    override def toString: String = {
        s"$card, x$quantity"
    }
}

val redBeanField = BeanField(redBean, 1)
val blueBeanField = BeanField(blueBean, 3)
redBeanField.toString

// Option - container for zero or one element of a given type
class Player(val playerName: String, val fields: Option[List[BeanField]], val cards: List[Card]) {
    def getBeanFields: String = {
        val beanFieldAsString = fields match {
            case Some(beanFields) => beanFields.map(field => field.toString).mkString(" | ")
            case None => "No bean field yet"
        }
        val beanFields = fields.map(field => field.toString)
        s"$playerName | " + beanFieldAsString + "\n"
    }

    override def toString(): String = {
        val cardListAsString = cards.map(_.toString).mkString(" | ")
        s"$playerName | " + cardListAsString + "\n"
    }
}


val playerJomi = Player("Jomi", Some(List(redBeanField, blueBeanField)), List(redBean, redBean, blueBean))
playerJomi.getBeanFields
val playerDaniel = Player("Daniel", None, List(redBean, blueBean, redBean))
playerDaniel.getBeanFields

class BohnanzaGame(val players: List[Player]) {
    // the toString method of player already returns "\n"
    def getAllPlayers : String = players.map(_.toString).mkString("")

    def getAllFields : String = players.map(_.getBeanFields).mkString("")
}

val game = BohnanzaGame(List(playerJomi, playerDaniel))
println(game.getAllPlayers)
println(game.getAllFields)
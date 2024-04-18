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

class BeanField(val card: Card, val quantity: Int) {
    override def toString: String = {
        s"$card, x$quantity"
    }
}

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

class BohnanzaGame(val players: List[Player]) {
    // the toString method of player already returns "\n"
    def getAllPlayers : String = players.map(_.toString).mkString("")

    def getAllFields : String = players.map(_.getBeanFields).mkString("")
}

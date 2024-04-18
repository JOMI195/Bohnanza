import org.scalatest.wordspec.AnyWordSpec

import org.scalatest.matchers.should.Matchers._

val redBean = Card("Red Bean", List(Map("cards" -> 3, "coins" -> 1), Map("cards" -> 5, "coins" -> 2)));
val blueBean = Card("Blue Bean", List(Map("cards" -> 2, "coins" -> 1), Map("cards" -> 4, "coins" -> 2)))

val redBeanField = BeanField(redBean, 1)
val blueBeanField = BeanField(blueBean, 3)

val playerJomi = Player("Jomi", Some(List(redBeanField, blueBeanField)), List(redBean, redBean, blueBean))
val playerDaniel = Player("Daniel", None, List(redBean, blueBean, redBean))

class CardSpec extends AnyWordSpec {
    "Card toString" should {
        "simply return the name of the card" in {
            blueBean.name shouldEqual "Blue Bean"
        }
    }

    "Card getCardValueInfo" should {
        "return correct representation of card infos" in {
            val infoText = s"${blueBean.name} card trading infos"
            blueBean.getCardValueInfo shouldEqual
            infoText + "\n" + 
              ("-" *  infoText.length) + "\n" + 
              "For x2 beans, you get x1 coins\n" +
              "For x4 beans, you get x2 coins"
        }
    }
}

class BeanFieldSpec extends AnyWordSpec {
    "BeanField toString" should {
        "simply return string representation of bean field" in {
            blueBeanField.toString shouldEqual "Blue Bean, x3"
        }
    }
}

class PlayerSpec extends AnyWordSpec {
    "Player getBeanFields" should {
        "return the string representation of many combined bean fields" in {
            playerJomi.getBeanFields shouldEqual "Jomi | Red Bean, x1 | Blue Bean, x3\n"
        }

        "return special text if the player has no bean fields" in {
            playerDaniel.getBeanFields shouldEqual "Daniel | No bean field yet\n"
        }
    }

    "Player toString" should {
        "return cards of a player" in {
            playerJomi.toString shouldEqual "Jomi | Red Bean | Red Bean | Blue Bean\n"
        }
    }
}

class BohnanzaGameSpec extends AnyWordSpec {
    val game = BohnanzaGame(List(playerJomi, playerDaniel))
    "BohnanzaGame getAllPlayers" should {
        "return the string representation of all players that are playing this game" in {
            game.getAllPlayers shouldEqual 
            "Jomi | Red Bean | Red Bean | Blue Bean\n" + 
            "Daniel | Red Bean | Blue Bean | Red Bean\n"
            

        }
    }

    "BohnanzaGame getAllFields" should {
        "return fields of all players" in {
            game.getAllFields shouldEqual
            "Jomi | Red Bean, x1 | Blue Bean, x3\n" +
            "Daniel | No bean field yet\n"
        }
    }
}
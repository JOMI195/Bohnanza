package bohnanza
import scala.io.StdIn.readLine

import bohnanza.model.*
import bohnanza.aview.*

object Bohnanza {
  val p1 = Player(
    name = "Jomi",
    coins = 0,
    beanFields = List(BeanField(None)),
    cards = List()
  )
  val p2 = Player(
    name = "Daniel",
    coins = 0,
    beanFields = List(BeanField(Option(Bean.Firebean), 4)),
    cards = List()
  )
  val d = Deck(cards = List(Bean.Firebean))
  val t = TurnOverField(cards = List())

  var game = Game(players = List(p1, p2), deck = d, turnOverField = t)
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    var input: String = ""

    while (input != "exit") {
      input = readLine()
      game = tui.processInputLine(input, game)
    }
  }
}

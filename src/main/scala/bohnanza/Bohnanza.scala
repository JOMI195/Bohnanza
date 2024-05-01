package bohnanza
import scala.io.StdIn.readLine

import bohnanza.model.*
import bohnanza.aview.*
import bohnanza.controller.*

object Bohnanza {
  val p1 = Player(
    name = "Jomi",
    coins = 0,
    beanFields = List(BeanField(None)),
    hand = Hand(List.empty)
  )
  val p2 = Player(
    name = "Daniel",
    coins = 0,
    beanFields = List(BeanField(Option(Bean.Firebean), 4)),
    hand = Hand(List.empty)
  )
  val d = Deck(cards = List(Bean.Firebean, Bean.Firebean, Bean.Firebean))
  val t = TurnOverField(cards = List())

  var game = Game(players = List(p1, p2), deck = d, turnOverField = t)
  val controller = Controller(game)
  val tui = new Tui(controller)

  def main(args: Array[String]): Unit = {
    var input: String = ""

    println("Starting new game...")
    while (input != "exit") {
      input = readLine()
      tui.processInputLine(input)
    }
  }
}

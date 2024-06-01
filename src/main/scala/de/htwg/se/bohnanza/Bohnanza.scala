package bohnanza
import scala.io.StdIn.readLine

import bohnanza.model.*
import bohnanza.aview.*
import bohnanza.controller.*
import bohnanza.aview.gui.*
import scalafx.application.Platform

object Bohnanza {
  val d = FullDeckCreateStrategy().createDeck()
  val t = TurnOverField(cards = List())

  val game = Game(
    players = List.empty,
    deck = d,
    turnOverField = t,
    currentPlayerIndex = -1
  )

  val controller = Controller(game)
  val tui = new Tui(controller)
  val gui = new Gui(controller)

  def main(args: Array[String]): Unit = {
    new Thread(() => {
      gui.main(Array.empty)
    }).start()

    var input: String = ""
    println("Starting new game...")
    println(
      "Please create players first with the command: createPlayer [playerName].\n"
    )
    while (input != "exit") {
      input = readLine()
      tui.processInputLine(input) match {
        case Some(output) => println(s"$output\n")
        case None         => {}
      }
    }
  }
}

package bohnanza
import scala.io.StdIn.readLine

import bohnanza.model.*
import bohnanza.aview.*

object Bohnanza {

  var game = new Game()
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    var input: String = ""

    while (input != "exit") {
      input = readLine()
      game = tui.processInputLine(input, game)
    }
  }
}

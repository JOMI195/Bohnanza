package de.htwg.se.bohnanza

import scala.io.StdIn.readLine
import scalafx.application.Platform

import de.htwg.se.bohnanza.model.GameComponent.DeckComponent.FullDeckCreateStrategy
import de.htwg.se.bohnanza.model.GameComponent.TurnOverFieldComponent.TurnOverField
import de.htwg.se.bohnanza.model.GameComponent.Game
import de.htwg.se.bohnanza.aview.Tui
import de.htwg.se.bohnanza.aview.gui.*
import de.htwg.se.bohnanza.controller.ControllerComponent.{Controller}

import BohanzaModule.given
import de.htwg.se.bohnanza.controller.ControllerComponent.IController

object Bohnanza {
  val controller = summon[IController]
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

      if (input != null) {
        tui.processInputLine(input) match {
          case Some(output) => println(s"$output\n")
          case None         => {}
        }
      }
    }
    Platform.exit()
  }
}

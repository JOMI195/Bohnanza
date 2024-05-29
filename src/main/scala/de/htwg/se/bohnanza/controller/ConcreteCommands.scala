package bohnanza.controller

import bohnanza.util.Command

case class DrawCommand(controller: Controller, playerIndex: Int)
    extends Command {

  def doStep: Unit = controller.game =
    controller.game.playerDrawCardFromDeck(playerIndex = playerIndex)

  def undoStep: Unit = {
    controller.game = controller.game.playerPutCardToDeck(playerIndex)
  }

  def redoStep: Unit = doStep

}

case class PlantCommand(
    controller: Controller,
    playerIndex: Int,
    beanFieldIndex: Int
) extends Command {

  def doStep: Unit = ???

  def undoStep: Unit = ???

  def redoStep: Unit = ???

}

case class NextPhaseCommand(controller: Controller) extends Command {

  def doStep: Unit = ???

  def undoStep: Unit = ???

  def redoStep: Unit = ???

}

case class HarvestCommand(
    controller: Controller,
    playerIndex: Int,
    beanFieldIndex: Int
) extends Command {

  def doStep: Unit = ???

  def undoStep: Unit = ???

  def redoStep: Unit = ???

}

case class TurnCommand(
    controller: Controller
) extends Command {

  def doStep: Unit = ???

  def undoStep: Unit = ???

  def redoStep: Unit = ???

}

case class TakeCommand(
    controller: Controller,
    playerIndex: Int,
    cardIndex: Int,
    beanFieldIndex: Int
) extends Command {

  def doStep: Unit = ???

  def undoStep: Unit = ???

  def redoStep: Unit = ???

}

package bohnanza.controller

import bohnanza.util.Command

case class DrawCommand(controller: Controller, playerIndex: Int)
    extends Command(controller) {

  def doStep: Unit = controller.game =
    controller.game.playerDrawCardFromDeck(playerIndex = playerIndex)
}

case class PlantCommand(
    controller: Controller,
    playerIndex: Int,
    beanFieldIndex: Int
) extends Command(controller) {

  def doStep: Unit = ???

}

case class NextPhaseCommand(controller: Controller)
    extends Command(controller) {

  def doStep: Unit = ???
}

case class HarvestCommand(
    controller: Controller,
    playerIndex: Int,
    beanFieldIndex: Int
) extends Command(controller) {

  def doStep: Unit = ???
}

case class TurnCommand(
    controller: Controller
) extends Command(controller) {

  def doStep: Unit = ???

}

case class TakeCommand(
    controller: Controller,
    playerIndex: Int,
    cardIndex: Int,
    beanFieldIndex: Int
) extends Command(controller) {

  def doStep: Unit = ???

}

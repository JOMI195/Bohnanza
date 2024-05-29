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

  def doStep: Unit = controller.game =
    controller.game.playerPlantCardFromHand(playerIndex, beanFieldIndex)

}

case class NextPhaseCommand(controller: Controller)
    extends Command(controller) {

  def doStep: Unit = {
    controller.phase = controller.phase.nextPhase
    controller.game = controller.phase.startPhase(controller.game)
  }
}

case class HarvestCommand(
    controller: Controller,
    playerIndex: Int,
    beanFieldIndex: Int
) extends Command(controller) {

  def doStep: Unit = controller.game =
    controller.game.playerHarvestField(playerIndex, beanFieldIndex)
}

case class TurnCommand(
    controller: Controller
) extends Command(controller) {

  def doStep: Unit = controller.game = controller.game.drawCardToTurnOverField()

}

case class TakeCommand(
    controller: Controller,
    playerIndex: Int,
    cardIndex: Int,
    beanFieldIndex: Int
) extends Command(controller) {

  def doStep: Unit = controller.game =
    controller.game.playerPlantFromTurnOverField(
      playerIndex,
      cardIndex,
      beanFieldIndex
    )

}

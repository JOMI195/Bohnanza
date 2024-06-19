package de.htwg.se.bohnanza.aview.gui.model

val selectionStyle =
  "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0, 5, 5); -fx-border-color: #FEED5D; -fx-border-width: 2; -fx-border-radius: 10px;"

class SelectionManager(
    var selectedBeanFieldIndex: Int = -1,
    var selectFromHand: Boolean = false,
    var selectedTurnOverFieldIndex: Int = -1
)

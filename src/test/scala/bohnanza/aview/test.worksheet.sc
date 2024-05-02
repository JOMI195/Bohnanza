import bohnanza.model.*
import bohnanza.controller.*
import bohnanza.aview.*

val initialPlayer =
  Player("Player1", List(BeanField(None)), 0, Hand(List.empty))
val initialPlayers = List(initialPlayer)
val initialDeck = Deck(List(Bean.Firebean, Bean.Firebean))
val emptyTurnOverField = TurnOverField(List.empty)
val initialGame = Game(initialPlayers, initialDeck, emptyTurnOverField)
val controller = Controller(initialGame)

val tui = new Tui(controller)

val errorMessage = "Invalid Input\n"

val stream = new java.io.ByteArrayOutputStream()
Console.withOut(stream) {
  tui.processInputLine("turn")
}
println(stream.toString)
val desiredOutput =
  "Turnoverfield: | Firebean | Firebean |\n" + "\n" + "Player Player1 | coins: 0 |\n" + "- Hand: | empty |\n" + "- Beanfields: | empty |"

desiredOutput.trim() == stream.toString.trim()
println(desiredOutput.trim())
println(stream.toString.trim())

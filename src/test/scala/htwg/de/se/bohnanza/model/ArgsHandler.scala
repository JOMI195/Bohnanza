import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import bohnanza.model.*

class HandlerSpec extends AnyWordSpec with Matchers {

  val p1 = Player(
    name = "Player1",
    coins = 0,
    beanFields = List(BeanField(None)),
    hand = Hand(List.empty)
  )
  val d = FullDeckCreateStrategy().createDeck()
  val t = TurnOverField(cards = List(Bean.Firebean))

  val initialGame = Game(
    players = List(p1),
    deck = d,
    turnOverField = t,
    currentPlayerIndex = 0
  )

  "ArgsHandler" should {

    "PlayerIndexHandler" should {
      val handler = PlayerIndexHandler(None)

      "return PlayerIndexError if player index is out of bounds" in {
        val args = Map(HandlerKey.PlayerFieldIndex.key -> 1)

        handler.check(
          args,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.PlayerIndexError
      }

      "return Success if player index is within bounds" in {
        val args = Map(HandlerKey.PlayerFieldIndex.key -> 0)

        handler.check(
          args,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.Success
      }
    }

    "BeanFieldIndexHandler" should {
      val handler = BeanFieldIndexHandler(None)

      "return PlayerIndexError if player index is out of bounds" in {
        val args = Map(
          HandlerKey.PlayerFieldIndex.key -> 1,
          HandlerKey.BeanFieldIndex.key -> 0
        )

        handler.check(
          args,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.PlayerIndexError
      }

      "return BeanFieldIndexError if bean field index is out of bounds" in {
        val args = Map(
          HandlerKey.PlayerFieldIndex.key -> 0,
          HandlerKey.BeanFieldIndex.key -> 1
        )

        handler.check(
          args,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.BeanFieldIndexError
      }

      "return Success if both indexes are within bounds" in {
        val args = Map(
          HandlerKey.PlayerFieldIndex.key -> 0,
          HandlerKey.BeanFieldIndex.key -> 0
        )

        handler.check(
          args,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.Success
      }
    }

    "TurnoverFieldIndexHandler" should {
      val handler = TurnoverFieldIndexHandler(None)

      "return TurnOverFieldIndexError if turnover field index is out of bounds" in {
        val args = Map(HandlerKey.TurnOverFieldIndex.key -> 1)

        handler.check(
          args,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.TurnOverFieldIndexError
      }

      "return Success if turnover field index is within bounds" in {
        val args = Map(HandlerKey.TurnOverFieldIndex.key -> 0)

        handler.check(
          args,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.Success
      }
    }

    "MethodHandler" should {
      val handler = MethodHandler(None)

      "return MethodError if method is not allowed in PlayCardPhase" in {
        val args = Map(HandlerKey.Method.key -> "invalidMethod")

        handler.check(
          args,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.MethodError
      }

      "return Success if method is allowed in PlayCardPhase" in {
        val args = Map(HandlerKey.Method.key -> "harvest")

        handler.check(
          args,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.Success
      }

      "return MethodError if method is not allowed in TradeAndPlantPhase" in {
        val args = Map(HandlerKey.Method.key -> "invalidMethod")

        handler.check(
          args,
          new TradeAndPlantPhase,
          initialGame
        ) shouldBe HandlerResponse.MethodError
      }

      "return Success if method is allowed in TradeAndPlantPhase" in {
        val args = Map(HandlerKey.Method.key -> "harvest")

        handler.check(
          args,
          new TradeAndPlantPhase,
          initialGame
        ) shouldBe HandlerResponse.Success
      }

      "return MethodError in DrawCardsPhase" in {
        val args = Map(HandlerKey.Method.key -> "harvest")

        handler.check(
          args,
          new DrawCardsPhase,
          initialGame
        ) shouldBe HandlerResponse.MethodError
      }

      "return ArgsError if method key is not present" in {
        val args = Map.empty[String, Any]

        handler.check(
          args,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.ArgsError
      }
    }

    "HandlerTemplate" should {
      case class MockHandler(
          response: HandlerResponse,
          nextHandler: Option[HandlerTemplate] = None
      ) extends HandlerTemplate {
        override val next: Option[HandlerTemplate] = nextHandler

        override def check(
            args: Map[String, Any],
            phase: PhaseState,
            game: Game
        ): HandlerResponse = {
          response
        }
      }

      "return its own response if it's not Success" in {
        val handler = MockHandler(HandlerResponse.PlayerIndexError)

        handler.checkOrDelegate(
          Map.empty,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.PlayerIndexError
      }

      "delegate to the next handler if its own response is Success" in {
        val nextHandler = MockHandler(HandlerResponse.BeanFieldIndexError)
        val handler = MockHandler(HandlerResponse.Success, Some(nextHandler))

        handler.checkOrDelegate(
          Map.empty,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.BeanFieldIndexError
      }

      "return Success if its own response is Success and there is no next handler" in {
        val handler = MockHandler(HandlerResponse.Success)

        handler.checkOrDelegate(
          Map.empty,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.Success
      }

      "delegate through a chain of handlers and return the first non-Success response" in {
        val thirdHandler = MockHandler(HandlerResponse.TurnOverFieldIndexError)
        val secondHandler =
          MockHandler(HandlerResponse.Success, Some(thirdHandler))
        val firstHandler =
          MockHandler(HandlerResponse.Success, Some(secondHandler))

        firstHandler.checkOrDelegate(
          Map.empty,
          new PlayCardPhase,
          initialGame
        ) shouldBe HandlerResponse.TurnOverFieldIndexError
      }
    }
  }
}

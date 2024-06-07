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
  val t = TurnOverField(cards = List(Bean.ChiliBean))

  val initialGame = Game(
    players = List(p1),
    deck = d,
    turnOverField = t,
    currentPlayerIndex = 0
  )

  "ArgsHandler" should {

    "TakeInvalidPlantHandler" should {
      val playerTest = Player(
        name = "Player1",
        coins = 0,
        beanFields = List(BeanField(Option(Bean.BlueBean), 1)),
        hand = Hand(List.empty)
      )
      val deck = FullDeckCreateStrategy().createDeck()
      val turnOverField =
        TurnOverField(cards = List(Bean.ChiliBean, Bean.BlueBean))

      val game = Game(
        players = List(playerTest),
        deck = deck,
        turnOverField = turnOverField,
        currentPlayerIndex = 0
      )
      val handler = TakeInvalidPlantHandler(None)

      "return TakeInvalidPlantError if bean to plant does not match" in {
        val args = Map(
          HandlerKey.Method.key -> "take",
          HandlerKey.PlayerFieldIndex.key -> 0,
          HandlerKey.BeanFieldIndex.key -> 0,
          HandlerKey.TurnOverFieldIndex.key -> 0
        )

        handler.check(
          args,
          new PlayCardPhase,
          game
        ) shouldBe HandlerResponse.TakeInvalidPlantError

      }

      "return Success if take is valid when a bean is allready planted" in {
        val p1 = Player(
          name = "Player1",
          coins = 0,
          beanFields = List(BeanField(Option(Bean.ChiliBean))),
          hand = Hand(List.empty)
        )
        val d = FullDeckCreateStrategy().createDeck()
        val t = TurnOverField(cards = List(Bean.ChiliBean))

        val initialGame1 = Game(
          players = List(p1),
          deck = d,
          turnOverField = t,
          currentPlayerIndex = 0
        )

        val args1 = Map(
          HandlerKey.Method.key -> "take",
          HandlerKey.PlayerFieldIndex.key -> 0,
          HandlerKey.BeanFieldIndex.key -> 0,
          HandlerKey.TurnOverFieldIndex.key -> 0
        )

        handler.check(
          args1,
          new PlayCardPhase,
          initialGame1
        ) shouldBe HandlerResponse.Success

      }
      "return Success if take is valid when no bean is planted" in {
        val p2 = Player(
          name = "Player1",
          coins = 0,
          beanFields = List(BeanField(None)),
          hand = Hand(List.empty)
        )

        val initialGame2 = Game(
          players = List(p2),
          deck = d,
          turnOverField = t,
          currentPlayerIndex = 0
        )

        val args2 = Map(
          HandlerKey.Method.key -> "take",
          HandlerKey.PlayerFieldIndex.key -> 0,
          HandlerKey.BeanFieldIndex.key -> 0,
          HandlerKey.TurnOverFieldIndex.key -> 0
        )

        handler.check(
          args2,
          new PlayCardPhase,
          initialGame2
        ) shouldBe HandlerResponse.Success
      }

      "return ArgsError if method is missing playerIndex" in {
        val args = Map(
          HandlerKey.Method.key -> "take",
          HandlerKey.BeanFieldIndex.key -> 0,
          HandlerKey.TurnOverFieldIndex.key -> 1
        )

        handler.check(
          args,
          new PlayCardPhase,
          game
        ) shouldBe HandlerResponse.ArgsError
      }

      "return ArgsError if method is missing beanFieldIndex" in {
        val args = Map(
          HandlerKey.Method.key -> "take",
          HandlerKey.PlayerFieldIndex.key -> 0,
          HandlerKey.TurnOverFieldIndex.key -> 1
        )

        handler.check(
          args,
          new PlayCardPhase,
          game
        ) shouldBe HandlerResponse.ArgsError
      }

      "return ArgsError if method is missing turnOverFieldIndex" in {
        val args = Map(
          HandlerKey.Method.key -> "take",
          HandlerKey.PlayerFieldIndex.key -> 0,
          HandlerKey.BeanFieldIndex.key -> 0
        )

        handler.check(
          args,
          new PlayCardPhase,
          game
        ) shouldBe HandlerResponse.ArgsError
      }

      "return ArgsError if method passes as arg" in {
        val args = Map(
          HandlerKey.PlayerFieldIndex.key -> 0
        )

        handler.check(
          args,
          new PlayCardPhase,
          game
        ) shouldBe HandlerResponse.ArgsError
      }
    }

    "InvalidPlantHandler" should {
      val playerTest = Player(
        name = "Player1",
        coins = 0,
        beanFields = List(
          BeanField(Option(Bean.BlueBean), 1),
          BeanField(Option(Bean.ChiliBean), 1)
        ),
        hand = Hand(List(Bean.BlueBean))
      )
      val deck = FullDeckCreateStrategy().createDeck()
      val turnOverField =
        TurnOverField(cards = List.empty)

      val game = Game(
        players = List(playerTest),
        deck = deck,
        turnOverField = turnOverField,
        currentPlayerIndex = 0
      )
      val handler = InvalidPlantHandler(None)

      "return InvalidPlantError if bean to plant does not match" in {
        val args = Map(
          HandlerKey.Method.key -> "plant",
          HandlerKey.PlayerFieldIndex.key -> 0,
          HandlerKey.BeanFieldIndex.key -> 1
        )

        handler.check(
          args,
          new PlayCardPhase,
          game
        ) shouldBe HandlerResponse.InvalidPlantError
      }

      "return Success if there is no bean on the beanfield yet." in {
        val noBeanPlayer = Player(
          name = "Player1",
          coins = 0,
          beanFields = List(
            BeanField(None)
          ),
          hand = Hand(List(Bean.BlueBean))
        )
        val game = Game(
          players = List(noBeanPlayer),
          deck = deck,
          turnOverField = turnOverField,
          currentPlayerIndex = 0
        )
        val args = Map(
          HandlerKey.Method.key -> "plant",
          HandlerKey.PlayerFieldIndex.key -> 0,
          HandlerKey.BeanFieldIndex.key -> 0
        )

        handler.check(
          args,
          new PlayCardPhase,
          game
        ) shouldBe HandlerResponse.Success
      }

      "return Success if take is valid" in {
        val args = Map(
          HandlerKey.Method.key -> "plant",
          HandlerKey.PlayerFieldIndex.key -> 0,
          HandlerKey.BeanFieldIndex.key -> 0
        )

        handler.check(
          args,
          new PlayCardPhase,
          game
        ) shouldBe HandlerResponse.Success
      }

      "return ArgsError if playerFieldIndex is missing" in {
        val args = Map(
          HandlerKey.Method.key -> "plant",
          HandlerKey.BeanFieldIndex.key -> 0
        )

        handler.check(
          args,
          new PlayCardPhase,
          game
        ) shouldBe HandlerResponse.ArgsError
      }

      "return ArgsError if beanFieldIndex is missing" in {
        val args = Map(
          HandlerKey.Method.key -> "plant",
          HandlerKey.PlayerFieldIndex.key -> 0
        )

        handler.check(
          args,
          new PlayCardPhase,
          game
        ) shouldBe HandlerResponse.ArgsError
      }

      "return ArgsError if method passes as arg" in {
        val args = Map(
          HandlerKey.PlayerFieldIndex.key -> 0
        )

        handler.check(
          args,
          new PlayCardPhase,
          game
        ) shouldBe HandlerResponse.ArgsError
      }
    }

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

      "return CurrentPlayerIndexError if player is not current player" in {
        val args = Map(HandlerKey.PlayerFieldIndex.key -> 0)
        val alteredGame = initialGame.copy(currentPlayerIndex = 1)

        handler.check(
          args,
          new PlayCardPhase,
          alteredGame
        ) shouldBe HandlerResponse.CurrentPlayerIndexError
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
      val handler = TurnOverFieldIndexHandler(None)

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

      "return MissingPlayerCreationError if no player is created on method next in GameInitializationPhase " in {
        val args = Map(HandlerKey.Method.key -> "next")
        val initialGame = Game(
          players = List.empty,
          deck = d,
          turnOverField = t,
          currentPlayerIndex = 0
        )

        handler.check(
          args,
          new GameInitializationPhase,
          initialGame
        ) shouldBe HandlerResponse.MissingPlayerCreationError
      }

      "return Success if method is allowed in GameInitializationPhase " in {
        val args1 = Map(HandlerKey.Method.key -> "next")

        handler.check(
          args1,
          new GameInitializationPhase,
          initialGame
        ) shouldBe HandlerResponse.Success

        val args2 = Map(HandlerKey.Method.key -> "createPlayer")

        handler.check(
          args2,
          new GameInitializationPhase,
          initialGame
        ) shouldBe HandlerResponse.Success
      }

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

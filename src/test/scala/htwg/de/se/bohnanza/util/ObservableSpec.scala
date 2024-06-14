import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.bohnanza.model.ArgsHandlerComponent.HandlerResponse
import de.htwg.se.bohnanza.util.*

// only test here the remove and notify function. The other functions were already being tested!
object testObserver extends Observer {
  override def update(error: HandlerResponse): Unit = {
    println("Error occurred.")
  }
  override def update(event: ObserverEvent): Unit = {
    println("Action occurred.")
  }
}

class ObservableSpec extends AnyWordSpec with Matchers {

  "Observer" should {
    val observer = testObserver
    val observable = new Observable
    observable.add(observer)
    "remove observer" in {
      observable.remove(observer)
      observable.subscribers shouldBe empty
    }

    "notify all subscribers" in {
      val observer = testObserver
      val observable = new Observable
      observable.add(observer)
      observable.notifyObservers(HandlerResponse.ArgsError)
    }
  }
}

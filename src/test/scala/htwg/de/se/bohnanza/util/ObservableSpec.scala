import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.util.{Observer, Observable}
import bohnanza.model.HandlerResponse
import bohnanza.util.ObserverEvent

// only test here the remove and notify function. The other functions were already being tested!
object testObserver extends Observer {
  override def update(error: HandlerResponse): Unit = {}
  override def update(event: ObserverEvent): Unit = {}
}

class ObservableSpec extends AnyWordSpec with Matchers {

  val observer = testObserver
  val observable = new Observable
  observable.add(observer)
  "Observer" should {
    "remove observer" in {
      observable.remove(observer)
      observable.subscribers shouldBe empty
    }

    "notify all subscribers" in {
      observable.notifyObservers(HandlerResponse.ArgsError)
    }
  }
}

/* import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import bohnanza.util.{Observer, Observable}

object testObserver extends Observer {
  override def update: Unit = {}
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
  }
}
 */

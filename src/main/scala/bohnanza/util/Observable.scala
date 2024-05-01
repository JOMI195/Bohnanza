package bohnanza.util

trait Observer {
  def update: Unit
}

class Observable {
  var observers: List[Observer] = List()

  def add(observer: Observer): Unit = observers = observers :+ observer

  def remove(observer: Observer): Unit = observers =
    observers.filterNot(o => o == observer)

  def notifyObservers: Unit = observers.foreach(o => o.update)
}

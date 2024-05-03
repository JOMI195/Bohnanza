package bohnanza.model

enum Bean(val quantityToCoins: Map[Int, Int], val frequency: Int) {

  case Firebean extends Bean(Map(3 -> 1, 4 -> 2, 6 -> 3, 7 -> 4), 18)
  case BlueBean extends Bean(Map(3 -> 1, 4 -> 2, 6 -> 3, 7 -> 4), 20)
}

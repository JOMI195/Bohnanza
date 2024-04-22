package bohnanza.model

enum Bean(quantityToCoins: Map[Int, Int], frequency: Int) {

  case Firebean extends Bean(Map(3 -> 1, 4 -> 2, 6 -> 3, 7 -> 4), 18)
}

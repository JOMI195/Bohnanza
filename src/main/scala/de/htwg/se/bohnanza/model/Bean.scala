package bohnanza.model

enum Bean(
    val quantityToCoins: Map[Int, Int],
    val frequency: Int,
    val shortName: String
) {

  case BlueBean extends Bean(Map(4 -> 1, 6 -> 2, 8 -> 3, 10 -> 4), 20, "blue")
  case ChiliBean extends Bean(Map(3 -> 1, 6 -> 2, 8 -> 3, 9 -> 4), 18, "chili")
  case StinkyBean
      extends Bean(Map(2 -> 1, 4 -> 2, 6 -> 3, 7 -> 4), 20, "stinky")
  case GreenBean extends Bean(Map(3 -> 1, 5 -> 2, 6 -> 3, 7 -> 4), 20, "green")
  case SoyBean extends Bean(Map(2 -> 1, 4 -> 2, 6 -> 3, 7 -> 4), 20, "soy")
  case BlackEyedBean
      extends Bean(Map(2 -> 1, 4 -> 2, 6 -> 3, 7 -> 4), 20, "black-eyed")
  case RedBean extends Bean(Map(2 -> 1, 3 -> 2, 4 -> 3, 5 -> 4), 20, "red")
  case GardenBean extends Bean(Map(2 -> 2, 3 -> 3), 20, "garden")
}

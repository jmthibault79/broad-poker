package poker

import poker.model.Card

trait TestHands {

  val lowPairCards = Seq(
    Card("2H"),
    Card("2D"),
    Card("4C"),
    Card("KC"),
    Card("AD")
  )
  val lowPairHighestCard = Card("AD")

  val highPairCards = Seq(
    Card("QH"),
    Card("QD"),
    Card("4C"),
    Card("KC"),
    Card("AD")
  )

  val twoPairCards = Seq(
    Card("2H"),
    Card("4D"),
    Card("4C"),
    Card("5C"),
    Card("5S")
  )

  val fourKindCards = Seq(
    Card("2H"),
    Card("2D"),
    Card("2C"),
    Card("2S"),
    Card("AD")
  )

  val threeKindCards = Seq(
    Card("7H"),
    Card("7D"),
    Card("7C"),
    Card("6S"),
    Card("QD")
  )

  val fullHouseCards1 = Seq(
    Card("KH"),
    Card("KD"),
    Card("KC"),
    Card("2S"),
    Card("2D")
  )

  val fullHouseCards2 = Seq(
    Card("KH"),
    Card("KD"),
    Card("2C"),
    Card("2S"),
    Card("2D")
  )

  val flushCards = Seq(
    Card("2H"),
    Card("4H"),
    Card("JH"),
    Card("AH"),
    Card("KH")
  )

  val straightCards = Seq(
    Card("2H"),
    Card("3C"),
    Card("4H"),
    Card("5D"),
    Card("6S")
  )

  val straightFlushCards = Seq(
    Card("2H"),
    Card("3H"),
    Card("4H"),
    Card("5H"),
    Card("6H")
  )

  val royalFlushCards = Seq(
    Card("9D"),
    Card("JD"),
    Card("QD"),
    Card("KD"),
    Card("AD")
  )

  val fullHouseCardsExt = Seq(
    Card("2H"),
    Card("2D"),
    Card("2C"),
    Card("KC"),
    Card("KS"),
    Card("AD")
  )

  val twoTriplesCardsExt = Seq(
    Card("2H"),
    Card("2D"),
    Card("2C"),
    Card("KC"),
    Card("KS"),
    Card("KH"),
    Card("AD")
  )

  val flushCardsExt = Seq(
    Card("2H"),
    Card("4H"),
    Card("3H"),
    Card("5H"),
    Card("7H"),
    Card("2D"),
    Card("4D"),
    Card("QD"),
    Card("3D"),
    Card("5D")
  )

  val straightCardsExt = Seq(
    Card("2H"),
    Card("3C"),
    Card("4H"),
    Card("5D"),
    Card("6S"),
    Card("7H"),
    Card("8D"),
    Card("9S")
  )

}

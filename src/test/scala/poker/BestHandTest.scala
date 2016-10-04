package poker

import org.scalatest._
import poker.model._

class BestHandTest extends FlatSpec with Matchers with TestHands {

  "BestHand" should "find the best" in {
    val expected1 = Set(
      Card("KC"),
      Card("KS"),
      Card("KH")
    )

    val lowTriple = Set(
      Card("2H"),
      Card("2D"),
      Card("2C")
    )

    val best1 = BestHand.best(twoTriplesCardsExt)
    best1 should contain allElementsOf expected1

    lowTriple should contain allElementsOf (best1.toSet -- expected1)

    val expected2 = Set(
      Card("2D"),
      Card("4D"),
      Card("QD"),
      Card("3D"),
      Card("5D")
    )

    BestHand.best(flushCardsExt) should contain theSameElementsAs expected2

    val expected3 = Set(
      Card("5D"),
      Card("6S"),
      Card("7H"),
      Card("8D"),
      Card("9S")
    )

    BestHand.best(straightCardsExt) should contain theSameElementsAs expected3

    val givenExample = Seq(
      Card("3H"),
      Card("7S"),
      Card("3S"),
      Card("QD"),
      Card("AH"),
      Card("3D"),
      Card("4S")
    )

    val givenExpected = Set(
      Card("3H"),
      Card("3S"),
      Card("3D"),
      Card("AH"),
      Card("QD")
    )

    BestHand.best(givenExample) should contain theSameElementsAs givenExpected
  }

  it should "handle the edge cases for Straight and Royal Flush" in {

    val trickyStraightFlushCards1 = Seq(
      Card("2H"),
      Card("3H"),
      Card("4H"),
      Card("5H"),
      Card("6H"),
      Card("7D")
    )

    val trickyStraightFlushCards2 = Seq(
      Card("2H"),
      Card("3H"),
      Card("4H"),
      Card("5H"),
      Card("6H"),
      Card("8H")
    )

    val trickyStraightFlushCards3 = Seq(
      Card("2H"),
      Card("3H"),
      Card("4H"),
      Card("5H"),
      Card("7H"),

      Card("9D"),
      Card("JS"),
      Card("QD"),
      Card("KS"),
      Card("AC")
    )

    BestHand.best(trickyStraightFlushCards1) should contain theSameElementsAs trickyStraightFlushCards1.take(5)
    BestHand.best(trickyStraightFlushCards2) should contain theSameElementsAs trickyStraightFlushCards2.take(5)
    BestHand.best(trickyStraightFlushCards3) should contain theSameElementsAs trickyStraightFlushCards3.take(5)

    val trickyNotRoyalFlushCards = Seq(
      Card("9D"),
      Card("JD"),
      Card("QD"),
      Card("KD"),
      Card("8D"),
      Card("AH")
    )

    BestHand.best(trickyNotRoyalFlushCards) should contain theSameElementsAs trickyNotRoyalFlushCards.take(5)
  }

}
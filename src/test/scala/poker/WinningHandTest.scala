package poker

import org.scalatest._
import poker.model._

class WinningHandTest extends FlatSpec with Matchers with TestHands {

  "ScoringHand" should "compare" in {
    val hand1 = HighCard(13, 11)
    val hand2 = HighCard(13, 11)
    val hand3 = HighCard(12, 11)
    val hand4 = Pair(2, 3)

    assertResult(0) { hand1 compare hand2 }
    assertResult(1) { hand1 compare hand3 }
    assertResult(-1) { hand1 compare hand4 }
    assertResult(1) { hand2 compare hand3 }
    assertResult(-1) { hand2 compare hand4 }
    assertResult(-1) { hand3 compare hand4 }

    val hand5 = HighCard(13, 12)

    assertResult(-1) { hand1 compare hand5 }

    val hand6 = Flush(13)
    val hand7 = RoyalFlush

    assertResult(-1) { hand6 compare hand7 }
  }

  "WinningHand" should "find the winner" in {
    assertResult((Pair(11, 13), highPairCards)) {
      WinningHand.winner(Seq(lowPairCards, highPairCards))
    }

    assertResult((FullHouse(2, 12), fullHouseCards2)) {
      WinningHand.winner(Seq(fullHouseCards2, threeKindCards))
    }

    assertResult((FullHouse(12, 2), fullHouseCards1)) {
      WinningHand.winner(Seq(fullHouseCards1, fullHouseCards2))
    }

    assertResult((TwoPair(5, 4, 2), twoPairCards)) {
      WinningHand.winner(Seq(twoPairCards, lowPairCards))
    }

    assertResult((FourOfAKind(2, 13), fourKindCards)) {
      WinningHand.winner(Seq(fourKindCards, threeKindCards))
    }

    assertResult((ThreeOfAKind(7, 11), threeKindCards)) {
      WinningHand.winner(Seq(threeKindCards, highPairCards))
    }

    assertResult((Flush(13), flushCards)) {
      WinningHand.winner(Seq(flushCards, threeKindCards))
    }

    assertResult((Straight(6), straightCards)) {
      WinningHand.winner(Seq(straightCards, twoPairCards))
    }

    assertResult((StraightFlush(6), straightFlushCards)) {
      WinningHand.winner(Seq(straightFlushCards, straightCards))
    }

    assertResult((RoyalFlush, royalFlushCards)) {
      WinningHand.winner(Seq(royalFlushCards, straightFlushCards))
    }
  }
}
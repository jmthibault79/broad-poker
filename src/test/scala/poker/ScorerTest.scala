package poker

import org.scalatest._
import poker.model._

class ScorerTest extends FlatSpec with Matchers with TestHands {
  "Highest" should "find the highest" in {
    assertResult(14) {
      Scorer.highest(lowPairCards)
    }

    assertResult(lowPairHighestCard.value) {
      Scorer.highest(lowPairCards)
    }
  }

  "Kicker" should "find the kicker" in {
    assertResult(13) {
      Scorer.kicker(lowPairCards, Set(14))
    }

    assertResult(13) {
      Scorer.kicker(lowPairCards, Set(14, 2))
    }

    assertResult(4) {
      Scorer.kicker(lowPairCards, Set(14, 2, 13))
    }
  }

  "handOfAKind" should "find the matching cards" in {
    assertResult(Map(2 -> Set(2))) { Scorer.handOfAKind(lowPairCards) }

    assertResult(Map.empty) { Scorer.handOfAKind(lowPairCards.tail) }

    assertResult(Map(2 -> Set(12))) { Scorer.handOfAKind(highPairCards) }

    assertResult(Map(2 -> Set(4, 5))) { Scorer.handOfAKind(twoPairCards) }

    assertResult(Map(4 -> Set(2))) { Scorer.handOfAKind(fourKindCards) }

    assertResult(Map(3 -> Set(7))) { Scorer.handOfAKind(threeKindCards) }
  }

  "Flush" should "find the matching cards" in {
    assertResult(None) {
      Scorer.isHandFlush(lowPairCards)
    }

    assertResult(Some(14)) {
      Scorer.isHandFlush(flushCards)
    }
  }

  "Straight" should "find the matching cards" in {
    assertResult(None) {
      Scorer.isHandStraight(lowPairCards)
    }

   assertResult(Some(6)) {
      Scorer.isHandStraight(straightCards)
    }
  }

  "Score" should "find the scoring hand" in {
    assertResult(Pair(2, lowPairHighestCard.value)) {
      Scorer.score(lowPairCards)
    }

    assertResult(Pair(12, 14)) {
      Scorer.score(highPairCards)
    }

    assertResult(FullHouse(13, 2)) {
      Scorer.score(fullHouseCards1)
    }

    assertResult(FullHouse(2, 13)) {
      Scorer.score(fullHouseCards2)
    }

    assertResult(TwoPair(5, 4, 2)) {
      Scorer.score(twoPairCards)
    }

    assertResult(FourOfAKind(2, 14)) {
      Scorer.score(fourKindCards)
    }

    assertResult(ThreeOfAKind(7, 12)) {
      Scorer.score(threeKindCards)
    }

    assertResult(Flush(14)) {
      Scorer.score(flushCards)
    }

    assertResult(Straight(6)) {
      Scorer.score(straightCards)
    }

    assertResult(StraightFlush(6)) {
      Scorer.score(straightFlushCards)
    }

    assertResult(RoyalFlush) {
      Scorer.score(royalFlushCards)
    }
  }
}
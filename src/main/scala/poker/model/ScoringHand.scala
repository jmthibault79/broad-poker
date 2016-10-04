package poker.model

sealed trait ScoringHand extends Ordered[ScoringHand] {
  def ranking: Int

  // enable implicit ordering for sorting
  import scala.math.Ordered.orderingToOrdered
  def compare(that: ScoringHand): Int = {
    val compared = this.ranking compare that.ranking

    if (compared != 0) compared
    else {
      (this, that) match {
        case (HighCard(v1, k1), HighCard(v2, k2))           => (v1, k1) compare (v2, k2)
        case (Pair(v1, k1), Pair(v2, k2))                   => (v1, k1) compare (v2, k2)
        case (TwoPair(lv1, hv1, k1), TwoPair(lv2, hv2, k2)) => (lv1, lv2, k1) compare (lv2, lv2, k2)
        case (ThreeOfAKind(v1, k1), ThreeOfAKind(v2, k2))   => (v1, k1) compare (v2, k2)
        case (Straight(v1), Straight(v2))                   => v1 compare v2
        case (Flush(v1), Flush(v2))                         => v1 compare v2
        case (FullHouse(v31, v21), FullHouse(v32, v22))     => (v31, v21) compare (v32, v22)
        case (FourOfAKind(v1, k1), FourOfAKind(v2, k2))     => (v1, k1) compare (v2, k2)
        case (StraightFlush(v1), StraightFlush(v2))         => v1 compare v2
        case _ => compared
      }
    }
  }

}

case class HighCard(value: Int, kicker: Int)                    extends ScoringHand { val ranking = 0 }
case class Pair(value: Int, kicker: Int)                        extends ScoringHand { val ranking = 1 }
case class TwoPair(lowValue: Int, highValue: Int, kicker: Int)  extends ScoringHand { val ranking = 2 }
case class ThreeOfAKind(value: Int, kicker: Int)                extends ScoringHand { val ranking = 3 }
case class Straight(highValue: Int)                             extends ScoringHand { val ranking = 4 }
case class Flush(highValue: Int)                                extends ScoringHand { val ranking = 5 }
case class FullHouse(trioValue: Int, pairValue: Int)            extends ScoringHand { val ranking = 6 }
case class FourOfAKind(value: Int, kicker: Int)                 extends ScoringHand { val ranking = 7 }
case class StraightFlush(highValue: Int)                        extends ScoringHand { val ranking = 8 }
case object RoyalFlush                                          extends ScoringHand { val ranking = 9 }


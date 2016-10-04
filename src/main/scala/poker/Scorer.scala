package poker

import poker.model._

object Scorer {
  def highest(cards: Traversable[Card]): Int = {
    cards map { _.value } max
  }

  def kicker(cards: Traversable[Card], filterOut: Set[Int]): Int = {
    cards map { _.value } filterNot { filterOut.contains } max
  }

  // returns a map of count -> seq[value] for all pairs+
  def handOfAKind(cards: Traversable[Card]): Map[Int, Set[Int]] = {
    val groupedByValue = cards.groupBy { _.value }
    val valuesAndCounts = groupedByValue collect { case (cardValue, cardsForValue) if cardsForValue.size > 1 => cardValue -> cardsForValue.size }
    val groupedByCounts = valuesAndCounts groupBy { case (cardValue, cardCount) => cardCount }
    val countsAndValues = groupedByCounts map { case (cardCount, cardVCs) => cardCount -> cardVCs.map { case (v, c) => v }.toSet }
    countsAndValues
  }

  def isHandFlush(cards: Traversable[Card]): Option[Int] = {
    val groupedBySuit = cards.groupBy { _.suit }
    val values = (groupedBySuit collect { case (_, cardsForSuit) if cardsForSuit.size >= 5 => cardsForSuit map { _.value } }).flatten
    if (values.isEmpty) None
    else Option(values.max)
  }

  def isHandStraight(cards: Traversable[Card]): Option[Int] = {
    val valuesHighToLow = cards.map { _.value }.toSeq.sorted.reverse

    if ((valuesHighToLow zip valuesHighToLow.tail).forall { case (a, b) => a - 1 == b }) valuesHighToLow.headOption
    else None
  }

  def score(cards: Traversable[Card]): ScoringHand = {
    if (cards.size != 5) throw new Exception ("Scorer.score() is only appropriate for standard hands of 5 cards")

    val straight = isHandStraight(cards)
    val flush = isHandFlush(cards)
    val kind = handOfAKind(cards)

    // match higher ranking hands first

    (straight, flush, kind) match {
      case (Some(straightHighest), Some(flushHighest), _) if straightHighest == Card.ACE => RoyalFlush

      case (Some(straightHighest), Some(flushHighest), _) => StraightFlush(straightHighest)

      case (_, _, ofKind) if ofKind.keys.nonEmpty && ofKind.keys.max >= 4 =>
        val quartetValue = (ofKind collect { case (count, values) if count >= 4 => values }).flatten.max
        val kickerValue = kicker(cards, Set(quartetValue))
        FourOfAKind(quartetValue, kickerValue)

      case (_, _, ofKind) if ofKind.keys.nonEmpty && ofKind.contains(3) && ofKind.contains(2) =>
        val trioValue = ofKind(3).max
        val pairValue = ofKind(2).max
        FullHouse(trioValue, pairValue)

      case (None, Some(flushHighest), _) => Flush(flushHighest)

      case (Some(straightHighest), None, _) => Straight(straightHighest)

      case (None, None, ofKind) if ofKind.keys.nonEmpty && ofKind.contains(3) =>
        val trioValue = ofKind(3).max
        val kickerValue = kicker(cards, Set(trioValue))
        ThreeOfAKind(trioValue, kickerValue)

      case (None, None, ofKind) if ofKind.keys.nonEmpty && ofKind.contains(2) && ofKind(2).size >= 2 =>
        val highPairValue = ofKind(2).max
        val lowPairValue = (ofKind(2).toSet - highPairValue).max
        val kickerValue = kicker(cards, Set(highPairValue, lowPairValue))
        TwoPair(highPairValue, lowPairValue, kickerValue)

      case (None, None, ofKind) if ofKind.keys.nonEmpty && ofKind.contains(2) =>
        val pairValue = ofKind(2).max
        val kickerValue = kicker(cards, Set(pairValue))
        Pair(pairValue, kickerValue)

      case _ =>
        val highestValue = highest(cards)
        val kickerValue = kicker(cards, Set(highestValue))
        HighCard(highestValue, kickerValue)
    }
  }
}

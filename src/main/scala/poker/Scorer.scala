package poker

import poker.model._

object Scorer {
  def highest(cards: Traversable[Card]): Int = {
    cards map { _.value } max
  }

  def kicker(cards: Traversable[Card], filterOut: Set[Int]): Int = {
    cards map { _.value } filterNot { filterOut.contains } max
  }

  def jokerCount(cards: Traversable[Card]): Int = {
    cards count { _.suit == JokerSuit }
  }

  // returns a map of count -> seq[value]
  def handOfAKind(cards: Traversable[Card]): Map[Int, Set[Int]] = {
    val groupedByValue = cards.filterNot { _.suit == JokerSuit }.groupBy { _.value }
    val valuesAndCounts = groupedByValue map { case (cardValue, cardsForValue) => cardValue -> cardsForValue.size }
    val groupedByCounts = valuesAndCounts groupBy { case (cardValue, cardCount) => cardCount }
    val countsAndValues = groupedByCounts map { case (cardCount, cardVCs) => cardCount -> cardVCs.map { case (v, c) => v }.toSet }
    countsAndValues
  }

  def isHandFlush(cards: Traversable[Card]): Option[Int] = {
    val jokers = jokerCount(cards)
    val nonJokers = 5 - jokers

    val groupedBySuit = cards.groupBy { _.suit }
    val values = (groupedBySuit collect { case (_, cardsForSuit) if cardsForSuit.size >= nonJokers => cardsForSuit map { _.value } }).flatten
    if (values.isEmpty) None
    else if (jokers > 0) Option(Card.MAX)   // Jokers can make any flush = the highest card
    else Option(values.max)
  }

  def isHandStraightNoJokers(values: Seq[Int]): Option[Int] = {
    if (values.size != 5) throw new Exception ("Scorer.isHandStraightNoJokers() is only appropriate for hands of 5 cards")

    val valuesHighToLow = values.sorted.reverse

    if ((valuesHighToLow zip valuesHighToLow.tail).forall { case (a, b) => a - 1 == b }) valuesHighToLow.headOption
    else None
  }

  def isHandStraight(cards: Traversable[Card]): Option[Int] = {
    val jokers = jokerCount(cards)
    if (jokers == 0) isHandStraightNoJokers(cards.toSeq.map { _.value })
    else {
      // 1 joker can help make a straight by equaling anywhere from beginning - 1 to end + 1
      // 2 jokers: beginning - 2 to end + 2

      val nonJokerValues = cards.toSeq collect { case Card(value, suit) if suit != JokerSuit => value }

      val min = Math.max(nonJokerValues.min - jokers, Card.MIN)
      val max = Math.min(nonJokerValues.max + jokers, Card.MAX)

      val jokerPossibilities = (min to max).combinations(jokers).toSeq
      val straights = jokerPossibilities flatMap { jokers => isHandStraightNoJokers(jokers ++ nonJokerValues) }

      if (straights.isEmpty) None
      else Option(straights.max)
    }
  }

  def score(cards: Seq[Card]): ScoringHand = {
    if (cards.size != 5) throw new Exception ("Scorer.score() is only appropriate for hands of 5 cards")

    val jokers = jokerCount(cards)
    val straight = isHandStraight(cards)
    val flush = isHandFlush(cards)
    val kind = handOfAKind(cards)   // not Joker-savvy

    // match higher ranking hands first

    (straight, flush, kind) match {
      case (_, _, ofKind) if ofKind.keys.max >= (5 - jokers) =>
        val fiveValue = (ofKind collect { case (count, values) if count >= (5 - jokers) => values }).flatten.max
        FiveOfAKind(fiveValue)

      case (Some(straightHighest), Some(flushHighest), _) if straightHighest == Card.ACE => RoyalFlush

      case (Some(straightHighest), Some(flushHighest), _) => StraightFlush(straightHighest)

      // 4, 3 + joker, 2 + 2 jokers
      case (_, _, ofKind) if ofKind.keys.max >= (4 - jokers) =>
        val quartetValue = (ofKind collect { case (count, values) if count >= (4 - jokers) => values }).flatten.max
        val kickerValue = kicker(cards, Set(quartetValue))
        FourOfAKind(quartetValue, kickerValue)

      // Full House = 3 + 2, 2 + 2 + joker
      // NOT 3 + 1 + joker -> that's 4 of a kind
      // NOT 2 + 1 + 2 jokers -> that's 4 of a kind

      // order for these two cases is not relevant:
      // they are mutually exclusive because 3 + joker = 4 of a kind instead

      case (_, _, ofKind) if ofKind.contains(3) && ofKind.contains(2) =>
        val trioValue = ofKind(3).max
        val pairValue = ofKind(2).max
        FullHouse(trioValue, pairValue)

      case (_, _, ofKind) if jokers > 0 && ofKind.contains(2) && ofKind(2).size >= 2 =>
        val trioValue = ofKind(2).max
        val pairValue = (ofKind(2) - trioValue).max
        FullHouse(trioValue, pairValue)

      case (None, Some(flushHighest), _) => Flush(flushHighest)

      case (Some(straightHighest), None, _) => Straight(straightHighest)

      case (None, None, ofKind) if ofKind.contains(3 - jokers) =>
        val trioValue = ofKind(3 - jokers).max
        val kickerValue = kicker(cards, Set(trioValue))
        ThreeOfAKind(trioValue, kickerValue)

      // any possible two pair with jokers is also a three of a kind
      case (None, None, ofKind) if ofKind.contains(2) && ofKind(2).size >= 2 =>
        val highPairValue = ofKind(2).max
        val lowPairValue = (ofKind(2) - highPairValue).max
        val kickerValue = kicker(cards, Set(highPairValue, lowPairValue))
        TwoPair(highPairValue, lowPairValue, kickerValue)

      case (None, None, ofKind) if ofKind.contains(2 - jokers) =>
        val pairValue = ofKind(2 - jokers).max
        val kickerValue = kicker(cards, Set(pairValue))
        Pair(pairValue, kickerValue)

      case _ =>
        val highestValue = highest(cards)
        val kickerValue = kicker(cards, Set(highestValue))
        HighCard(highestValue, kickerValue)
    }
  }
}

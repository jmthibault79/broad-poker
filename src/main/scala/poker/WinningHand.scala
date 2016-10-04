package poker

import poker.model.{Card, ScoringHand}

object WinningHand {
  def winner(hands: Traversable[Seq[Card]]): (ScoringHand, Traversable[Card]) = {
    val scoringHands = hands map { h => (Scorer.score(h), h) }
    scoringHands maxBy { case (scoringHand, hand) => scoringHand }
  }
}

package poker

import poker.model.Card

object BestHand {
  def best(cards: Seq[Card]): Traversable[Card] = {
    if (cards.size < 5) throw new Exception("BestHand.best() is only appropriate for hands of size 5 or greater")
    else if (cards.size == 5) cards
    else {
      val subHands = cards.toSet.subsets(5).toSeq map { _.toSeq }
      WinningHand.winner(subHands) match { case (scoringHand, hand) => hand }
    }
  }
}
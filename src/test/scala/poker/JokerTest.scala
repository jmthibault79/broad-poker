package poker

import org.scalatest.{FlatSpec, Matchers}
import poker.model._

class JokerTest extends FlatSpec with Matchers with TestHands {

  "Cards" should "convert appropriately" in {
    assertResult("Joker") {
      Card("Jo").toEnglish
    }
  }

  val jokerMakesPair = Seq(
    Card("Jo"),
    Card("8D"),
    Card("2H"),
    Card("3D"),
    Card("7C")
  )

  val jokerOnlyPair = Seq(
    Card("Jo"),
    Card("Jo"),
    Card("2H"),
    Card("3D"),
    Card("7C")
  )

  val jokerPlusPair = Seq(
    Card("Jo"),
    Card("7D"),
    Card("2H"),
    Card("3D"),
    Card("7C")
  )
  val jokersMakeThree = Seq(
    Card("Jo"),
    Card("Jo"),
    Card("9H"),
    Card("3D"),
    Card("4C")
  )

  val jokerMakesFlush = Seq(
    Card("Jo"),
    Card("8D"),
    Card("2D"),
    Card("3D"),
    Card("7D")
  )

  val jokerMakesFlush2 = Seq(
    Card("Jo"),
    Card("Jo"),
    Card("2D"),
    Card("3D"),
    Card("7D")
  )

  val jokerMakesStraight = Seq(
    Card("Jo"),
    Card("8D"),
    Card("9H"),
    Card("6D"),
    Card("7C")
  )

  val jokerInStraight = Seq(
    Card("Jo"),
    Card("8D"),
    Card("4H"),
    Card("5D"),
    Card("7C")
  )

  val joker2Straight = Seq(
    Card("Jo"),
    Card("8D"),
    Card("Jo"),
    Card("5D"),
    Card("7C")
  )

  val jokerMakesFour = Seq(
    Card("Jo"),
    Card("8D"),
    Card("8C"),
    Card("8S"),
    Card("7D")
  )

  val jokerMakesFour2 = Seq(
    Card("Jo"),
    Card("8D"),
    Card("8C"),
    Card("Jo"),
    Card("7D")
  )

  val jokerMakesFullHouse = Seq(
    Card("Jo"),
    Card("8D"),
    Card("8C"),
    Card("7S"),
    Card("7D")
  )

  val jokerMakesStraightFlush = Seq(
    Card("Jo"),
    Card("8D"),
    Card("6D"),
    Card("10D"),
    Card("7D")
  )

  val jokerMakesStraightFlush2 = Seq(
    Card("Jo"),
    Card("8D"),
    Card("6D"),
    Card("10D"),
    Card("Jo")
  )

  val jokerMakesRoyalFlush = Seq(
    Card("Jo"),
    Card("10S"),
    Card("JS"),
    Card("QS"),
    Card("KS")
  )

  val jokerMakesRoyalFlush2 = Seq(
    Card("Jo"),
    Card("10S"),
    Card("Jo"),
    Card("QS"),
    Card("KS")
  )

  val jokerMakesFive = Seq(
    Card("Jo"),
    Card("8D"),
    Card("8C"),
    Card("8S"),
    Card("8H")
  )

  val jokerMakesFive2 = Seq(
    Card("Jo"),
    Card("8D"),
    Card("8C"),
    Card("Jo"),
    Card("8H")
  )

  "Flush" should "find the matching cards" in {
    assertResult(Some(Card.MAX)) {
      Scorer.isHandFlush(jokerMakesFlush)
    }
  }

  "Straight" should "find the matching cards" in {
    assertResult(Some(10)) {
      Scorer.isHandStraight(jokerMakesStraight)
    }

    assertResult(Some(8)) {
      Scorer.isHandStraight(jokerInStraight)
    }
  }

  // TODO: two jokers
  // TODO: best hand
  // TODO: winning hand

  "Score" should "find the scoring hand" in {
    assertResult(Pair(8, 7)) {
      Scorer.score(jokerMakesPair)
    }

    assertResult(ThreeOfAKind(7, 3)) {
      Scorer.score(jokerOnlyPair)
    }

    assertResult(ThreeOfAKind(7, 3)) {
      Scorer.score(jokerPlusPair)
    }

    assertResult(ThreeOfAKind(9, 4)) {
      Scorer.score(jokersMakeThree)
    }

    assertResult(Flush(Card.MAX)) {
      Scorer.score(jokerMakesFlush)
    }

    assertResult(Flush(Card.MAX)) {
      Scorer.score(jokerMakesFlush2)
    }

    assertResult(Straight(10)) {
      Scorer.score(jokerMakesStraight)
    }

    assertResult(Straight(8)) {
      Scorer.score(jokerInStraight)
    }

    assertResult(Straight(9)) {
      Scorer.score(joker2Straight)
    }

    assertResult(FourOfAKind(8, 7)) {
      Scorer.score(jokerMakesFour)
    }

    assertResult(FourOfAKind(8, 7)) {
      Scorer.score(jokerMakesFour2)
    }

    assertResult(FullHouse(8, 7)) {
      Scorer.score(jokerMakesFullHouse)
    }

    assertResult(StraightFlush(10)) {
      Scorer.score(jokerMakesStraightFlush)
    }

    assertResult(StraightFlush(10)) {
      Scorer.score(jokerMakesStraightFlush2)
    }

    assertResult(RoyalFlush) {
      Scorer.score(jokerMakesRoyalFlush)
    }

    assertResult(RoyalFlush) {
      Scorer.score(jokerMakesRoyalFlush2)
    }

    assertResult(FiveOfAKind(8)) {
      Scorer.score(jokerMakesFive)
    }

    assertResult(FiveOfAKind(8)) {
      Scorer.score(jokerMakesFive2)
    }
  }
}

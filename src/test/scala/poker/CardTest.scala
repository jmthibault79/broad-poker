package poker

import org.scalatest._
import poker.model.{Card, HeartSuit}

class CardTest extends FlatSpec with Matchers {

  "Cards" should "convert appropriately" in {
    assertResult("2 of Hearts") {
      Card(2, HeartSuit).toEnglish
    }

    assertResult("2 of Hearts") {
      Card("2H").toEnglish
    }

    assertResult("9 of Hearts") {
      Card("9H").toEnglish
    }

    assertResult("J of Hearts") {
      Card("JH").toEnglish
    }

    assertResult("Q of Hearts") {
      Card("QH").toEnglish
    }

    assertResult("K of Hearts") {
      Card("KH").toEnglish
    }

    assertResult("A of Hearts") {
      Card("AH").toEnglish
    }

    intercept[Exception] {
      Card("1H")
    }

    intercept[Exception] {
      Card("3Z")
    }

    intercept[Exception] {
      Card("11H")
    }

    assertResult("10 of Hearts") {
      Card("10H").toEnglish
    }

    assertResult("9 of Spades") {
      Card("9S").toEnglish
    }

    assertResult("9 of Diamonds") {
      Card("9D").toEnglish
    }

    assertResult("9 of Clubs") {
      Card("9C").toEnglish
    }
  }
}
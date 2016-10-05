package poker.model

case class Card(value: Int, suit: Suit) {
  def toEnglish = suit match {
    case JokerSuit => "Joker"
    case s => s"${Card.strVal(value)} of ${s.word}s"
  }
}

object Card {
  def DEUCE = 2
  def ACE = 14

  def MIN = DEUCE
  def MAX = ACE

  def numberInRange(v: Int): Boolean = v >= 2 && v <= 10

  def strVal(v: Int): String = v match {
    case 11 => "J"
    case 12 => "Q"
    case 13 => "K"
    case 14 => "A"
    case value if Card.numberInRange(value) => value.toString
    case _ => throw new Exception(s"Invalid card value $v")
  }

  def apply(s: String): Card = {
    if (s.length < 2 || s.length > 3) throw new Exception(s"Invalid card $s")
    else if (s == "Jo") Card(-1, JokerSuit)
    else {
      val suit = Suit.suitFromLetter(s.last)

      val value = s.dropRight(1) match {
        case "J" => 11
        case "Q" => 12
        case "K" => 13
        case "A" => 14
        case v if numberInRange(v.toInt) => v.toInt
        case bad => throw new Exception(s"Invalid card value $bad")
      }

      Card(value, suit)
    }
  }
}
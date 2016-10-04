package poker.model

case class Card(value: Int, suit: Suit) {
  def toEnglish = s"${Card.strVal(value)} of ${suit.word}s"
}

object Card {
  def ACE = 13

  def valueInRange(v: Int): Boolean = v >= 2 && v <= 13

  def strVal(v: Int): String = v match {
    case 10 => "J"
    case 11 => "Q"
    case 12 => "K"
    case 13 => "A"
    case value if Card.valueInRange(value) => value.toString
    case _ => throw new Exception(s"Invalid card value $v")
  }

  def apply(s: String): Card = {
    if (s.length != 2) throw new Exception(s"Invalid card $s")

    val value = s.head.toString match {
      case "J" => 10
      case "Q" => 11
      case "K" => 12
      case "A" => 13
      case v if valueInRange(v.toInt) => v.toInt
      case _ => throw new Exception(s"Invalid card value ${s.head}")
    }

    val suit = Suit.suitFromLetter(s.tail.head)

    Card(value, suit)
  }
}
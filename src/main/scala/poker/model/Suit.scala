package poker.model

sealed trait Suit {
  def word: String
}

object Suit {
  def suitFromLetter(c: Char): Suit = c match {
    case 'H' => HeartSuit
    case 'D' => DiamondSuit
    case 'S' => SpadeSuit
    case 'C' => ClubSuit
    case _ => throw new Exception(s"Invalid suit $c")
  }
}

case object HeartSuit   extends Suit { val word = "Heart" }
case object DiamondSuit extends Suit { val word = "Diamond" }
case object SpadeSuit   extends Suit { val word = "Spade" }
case object ClubSuit    extends Suit { val word = "Club" }

case object JokerSuit   extends Suit { val word = "Joker" }

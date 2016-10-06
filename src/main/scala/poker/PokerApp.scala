package poker

object PokerApp {

  // not very useful yet.  Just parses an input hand
  // sbt "run src/main/resources/input1.txt"

  def main(args : Array[String]) {
    val cards = Parser.parse(args(0))
    cards foreach { c => println(c.toEnglish) }
  }
}

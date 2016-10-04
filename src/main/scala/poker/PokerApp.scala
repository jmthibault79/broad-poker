package poker

object PokerApp {
  def main(args : Array[String]) {
    val cards = Parser.parse(args(0))
    cards foreach { c => println(c.toEnglish) }
  }
}

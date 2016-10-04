package poker

import poker.model.Card

import spray.json._
import spray.json.DefaultJsonProtocol._

object Parser {
  def parse(filename: String): Iterable[Card] = {
    val json = io.Source.fromFile(filename).mkString.parseJson

    json match {
      case JsArray(values) => values map { v => Card(v.convertTo[String]) }
      case _ => throw new Exception("Invalid JSON input")
    }
  }
}

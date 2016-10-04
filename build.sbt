name := "broad-poker-initial"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "io.spray" %% "spray-json" % "1.3.1",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

mainClass in (Compile, run) := Some("poker.PokerApp")
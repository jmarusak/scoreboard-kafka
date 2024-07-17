package scoreboard

import spray.json._

import scoreboard.Score
import scoreboard.ScoreJsonProtocol._

import scoreboard.KafkaClient
import scoreboard.KafkaMockup

object ScorecardProducer {
  def parseArgs(args: Array[String]): (String, String, String) = {
    if (args.length != 6) {
      println("Usage: scoreboard.ScorecardProducer --court <number> --blue <points> --red <points>")
      System.exit(1)
    }

    var court, blue, red = ""

    args.sliding(2, 2).toList.collect {
      case Array("--court", arg: String) => court = arg
      case Array("--blue", arg: String) => blue = arg
      case Array("--red", arg: String) => red = arg
    }

    (court, blue, red)
  }

  def main(args: Array[String]): Unit = {
    println("Scorecard Producer started...")

    val (court, blue, red) = parseArgs(args)
    val score = Score(court, blue.toInt, red.toInt)
    val message = score.toJson.compactPrint

    println(message)
    //KafkaMockup.produce("score", message)
    KafkaClient.produce("score", message)
    println("Score posted.")
  }
}

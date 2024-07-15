package scoreboard

import spray.json._
import org.slf4j.LoggerFactory

import scoreboard.Score
import scoreboard.ScoreJsonProtocol._

import scoreboard.KafkaClient
import scoreboard.KafkaMockup

object ScoreProducer {
  private val logger = LoggerFactory.getLogger(getClass)

  def parseArgs(args: Array[String]): (String, String, String) = {
    if (args.length != 6) {
      println("Usage: scoreboard.ScoreProducer --court <number> --A <points> --B <points>")
      System.exit(1)
    }

  var court, a, b = ""

  args.sliding(2, 2).toList.collect {
    case Array("--court", arg: String) => court = arg
    case Array("--A", arg: String) => a = arg
    case Array("--B", arg: String) => b = arg
  }

  (court, a, b)
}

  def main(args: Array[String]): Unit = {
    logger.info("ScoreProducer started...")

    val (court, a, b) = parseArgs(args)
    val score = Score(court, a.toInt, b.toInt)
    val message = score.toJson.compactPrint

    println(message)
    //KafkaMockup.produce("score", message)
    KafkaClient.produce("score", message)
    println("Score posted.")
  }
}

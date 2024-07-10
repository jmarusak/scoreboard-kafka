package scoreboard

import spray.json._

import scoreboard.Play
import scoreboard.PlayJsonProtocol._

import scoreboard.KafkaMockup

object PlayProducer {
  def parseArgs(args: Array[String]): (String, String, String) = {
    if (args.length != 6) {
      println("Usage: producers.PlayProducer --court <number> --teamA <points> --teamB <points>")
      System.exit(1)
    }
  
  var court, teamA, teamB = ""

  args.sliding(2, 2).toList.collect {
    case Array("--court", arg: String) => court = arg
    case Array("--teamA", arg: String) => teamA = arg
    case Array("--teamB", arg: String) => teamB = arg
  }

  (court, teamA, teamB)
}

  def main(args: Array[String]): Unit = {
    println("Producer started...")

    val (court, teamA, teamB) = parseArgs(args)
    val play = Play(court, teamA.toInt, teamB.toInt)
    val json = play.toJson.compactPrint
    
    println(s"$play posted.")

    KafkaMockup.produce(json)
    KafkaMockup.consume()
  }
}

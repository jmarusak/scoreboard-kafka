package scoreboard

import spray.json._

import scoreboard.Score
import scoreboard.ScoreJsonProtocol._

import scoreboard.KafkaMockup

object ScoreProducer {
  def main(args: Array[String]): Unit = {
    println("ScoreProducer started...")

    val messages = KafkaMockup.consume("play")
    val plays = messages.map(_.parseJson.convertTo[Score])

    // Calculate the scores
    val scoresMap = plays.groupBy(_.court).view.mapValues(plays => 
      Score(
        plays.map(_.court).min,
        plays.map(_.teamA).sum,
        plays.map(_.teamB).sum))

    val scores = scoresMap.values.toList

    for (score <- scores) {
      val message = score.toJson.compactPrint
      KafkaMockup.produce("score", message)
      println(message)
    }

    println("Scores posted.")
  }
}

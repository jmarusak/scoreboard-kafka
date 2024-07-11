package scoreboard

import spray.json._

case class Score(
  court: String,
  teamA: Int,
  teamB: Int
)

object ScoreJsonProtocol extends DefaultJsonProtocol {
  implicit val ScoreFormat: JsonFormat[Score] = jsonFormat3(Score)
}

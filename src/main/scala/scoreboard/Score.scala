package scoreboard

import spray.json._

case class Score(
  court: String,
  blue: Int,
  red: Int,
  playtime: Long
)

object ScoreJsonProtocol extends DefaultJsonProtocol {
  implicit val ScoreFormat: JsonFormat[Score] = jsonFormat4(Score)
}

package scoreboard

import spray.json._

/**
 * Represents a score update with court, blue team score, red team score, and playtime.
 *
 * @param court The court number.
 * @param blue The blue team's score.
 * @param red The red team's score.
 * @param playtime The time of the score update.
 */
case class Score(
  court: String,
  blue: Int,
  red: Int,
  playtime: Long
)

/**
 * Provides JSON serialization and deserialization for the Score case class.
 */
object ScoreJsonProtocol extends DefaultJsonProtocol {
  implicit val ScoreFormat: JsonFormat[Score] = jsonFormat4(Score)
}

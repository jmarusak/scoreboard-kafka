package scoreboard

import spray.json._

case class Play(
  court: String,
  teamA: Int,
  teamB: Int
)

object PlayJsonProtocol extends DefaultJsonProtocol {
  implicit val playFormat: JsonFormat[Play] = jsonFormat3(Play)
}

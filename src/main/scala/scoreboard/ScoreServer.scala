package scoreboard

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route.seal

import scala.io.StdIn

import spray.json._

import scoreboard.Score
import scoreboard.ScoreJsonProtocol._

import scoreboard.KafkaMockup

object ScoreServer {
  def main(args: Array[String]): Unit = {
    val messages = KafkaMockup.consume("score")
    messages.foreach(println)

    implicit val system = ActorSystem(Behaviors.empty, "ScoreServer")
    implicit val executionContext = system.executionContext

    val route =
      path("scores") {
        get {
          complete {
            messages.toJson.compactPrint
          }
        }
      }

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(seal(route))
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")

    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}

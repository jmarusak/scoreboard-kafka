package scoreboard

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpRequest, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object ScorecardServer extends App {
  implicit val system: ActorSystem = ActorSystem("akka-http-server")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  // Route handling POST requests to /score
  val routes = path("score") {
    post {
      entity(as[String]) { requestBody =>
        println(requestBody)
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"Received: $requestBody"))
      }
    }
  }

  val bindingFuture = Http().bindAndHandle(routes, "localhost", 8081)

  println(s"Scorecard Server online at http://localhost:8081/\nPress RETURN to stop...")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}

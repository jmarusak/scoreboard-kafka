package scoreboard

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpRequest, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object ScorecardServer extends App {
  implicit val system: ActorSystem = ActorSystem("akka-http-server")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val kafkaServer = "localhost:9092"
  val kafkaProps = new Properties()
  kafkaProps.put("bootstrap.servers", kafkaServer)
  kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](kafkaProps)

  // Route handling POST requests to /score
  val routes = path("score") {
    post {
      entity(as[String]) { requestBody =>
        println(requestBody)
        try {
          val topic = "score"
          val key = System.currentTimeMillis().toString
          val record = new ProducerRecord[String, String](topic, key, requestBody)
          producer.send(record)
        } catch {
          case e: Exception =>
            println("Error connecting to Kafka producer: " + e.getMessage)
        }
        complete(HttpEntity(ContentTypes.`application/json`, """{"status": "200"}"""))
      }
    }
  }

  val bindingFuture = Http().bindAndHandle(routes, "localhost", 8081)

  println(s"Scorecard Server online at http://localhost:8081")
  println("Press ENTER to stop the server...")
  StdIn.readLine()

  producer.close()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}

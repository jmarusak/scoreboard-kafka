package scoreboard

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives.{handleWebSocketMessages, path}
import akka.stream.ActorMaterializer
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Sink, Source, SourceQueueWithComplete}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

import scala.collection.JavaConverters._

import scoreboard.KafkaClient
import scoreboard.KafkaMockup

object WebSocket {

  private var browserConnections: List[TextMessage => Unit] = List()

  def listen(): Flow[Message, Message, NotUsed] = {

    val inbound: Sink[Message, Any] = Sink.foreach(_ => ())
    val outbound: Source[Message, SourceQueueWithComplete[Message]] = Source.queue[Message](16, OverflowStrategy.fail)

    Flow.fromSinkAndSourceMat(inbound, outbound)((_, outboundMat) => {
      browserConnections ::= outboundMat.offer
      NotUsed
    })
  }

  def sendText(text: String): Unit = {
    for (connection <- browserConnections) connection(TextMessage.Strict(text))
  }
}

object WebSocketServer extends App {

  implicit val actorSystem: ActorSystem = ActorSystem("system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val route = path("score") {
    handleWebSocketMessages(WebSocket.listen())
  }

  Http().bindAndHandle(route, "localhost", 8080).onComplete {
    case Success(binding)   =>
      println(s"Listening on ${binding.localAddress.getHostString}:${binding.localAddress.getPort}")
    case Failure(exception) => throw exception
  }
  
  // run server for certain time
  val stopServerTime = System.currentTimeMillis() + 30 * 1000 // 30 seconds

  // using KafkaMockup (messages in a file)
  /*
  try {
    while (System.currentTimeMillis() < stopServerTime) {  
      val message = KafkaMockup.consume("score")
      WebSocket.sendText(message)
      println(s"Pushed: $message")
    }
  } finally {
      actorSystem.terminate()
      println("Server stopped after 30 seconds")
  }
  */
  
  // using KafkaClient (messages in a Kafka topic)
  val consumer = KafkaClient.consume("score")

  try {
    while (System.currentTimeMillis() < stopServerTime) {  
      val records = consumer.poll(java.time.Duration.ofMillis(100))
      for (record <- records.asScala) {
        val message = record.value()
        WebSocket.sendText(message)
        println(s"Pushed: $message")
      }
    }
  } finally {
    consumer.close()
    actorSystem.terminate()
    println("Server stopped after 30 seconds")
  }
}

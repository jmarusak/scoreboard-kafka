package scoreboard

import spray.json._

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import java.util.Properties

import scoreboard.Score
import scoreboard.ScoreJsonProtocol._

object ScoreProducer {
  def parseArgs(args: Array[String]): (String, String, String) = {
    if (args.length != 6) {
      println("Usage: scoreboard.ScoreProducer --court <number> --blue <points> --red <points>")
      System.exit(1)
    }

    var court, blue, red = ""
    args.sliding(2, 2).toList.collect {
      case Array("--court", arg: String) => court = arg
      case Array("--blue", arg: String) => blue = arg
      case Array("--red", arg: String) => red = arg
    }

    (court, blue, red)
  }

  def main(args: Array[String]): Unit = {
    val topic = "score"
    val kafkaServer = "localhost:9092"
    val kafkaProps = new Properties()
    kafkaProps.put("bootstrap.servers", kafkaServer)
    kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val (court, blue, red) = parseArgs(args)
    val playtime = System.currentTimeMillis
    val score = Score(court, blue.toInt, red.toInt, playtime)
    val message = score.toJson.compactPrint

    val producer = new KafkaProducer[String, String](kafkaProps)
    try {
      val key = System.currentTimeMillis().toString
      val record = new ProducerRecord[String, String](topic, key, message)
      producer.send(record)
      println(message)
    } catch {
      case e: Exception =>
        println("Error connecting to Kafka producer: " + e.getMessage)
    } finally {
      producer.close()
    }
  }
}

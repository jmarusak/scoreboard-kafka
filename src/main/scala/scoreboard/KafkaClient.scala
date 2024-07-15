package scoreboard

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import java.util.Properties
import java.time.Duration

object KafkaClient {
  def produce(topic: String, message: String): Unit = {
    val kafkaServer = "localhost:9092"

    val kafkaProps = new Properties()
    kafkaProps.put("bootstrap.servers", kafkaServer)
    kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    
    val producer = new KafkaProducer[String, String](kafkaProps)
    try {
      val record = new ProducerRecord[String, String]("score", "key", "value")
      producer.send(record)
    } catch {
      case e: Exception =>
        println("Error connecting to Kafka producer: " + e.getMessage)
    } finally {
      producer.close()
    }
  }
}

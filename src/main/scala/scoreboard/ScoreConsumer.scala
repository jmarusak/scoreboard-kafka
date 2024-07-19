package scoreboard

import org.apache.kafka.clients.consumer.{KafkaConsumer, ConsumerRecords, ConsumerConfig}
import java.util.Properties
import java.time.Duration
import scala.collection.JavaConverters._

object ScoreConsumer {
  def main(args: Array[String]): Unit = {
    val topic = "score"
    val kafkaServer = "localhost:9092"
    val kafkaProps = new Properties()
    
    kafkaProps.put("bootstrap.servers", kafkaServer)
    kafkaProps.put("group.id", s"$topic-group-all")
    kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    kafkaProps.put("auto.offset.reset", "earliest") // messages from beginning
  
    val consumer = new KafkaConsumer[String, String](kafkaProps)
    consumer.subscribe(java.util.Collections.singletonList(topic))
    try {
      val records = consumer.poll(java.time.Duration.ofMillis(100))
      for (record <- records.asScala) {
        val message = record.value()
        println(message)
      }
    } catch {
      case e: Exception =>
        println("Error connecting to Kafka consumer: " + e.getMessage)
    } finally {
      consumer.close()
    }
  }
}

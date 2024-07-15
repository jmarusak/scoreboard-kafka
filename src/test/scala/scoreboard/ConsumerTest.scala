package kafkaland

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.clients.consumer.{KafkaConsumer, ConsumerRecords, ConsumerConfig}
import java.util.Properties
import java.time.Duration

object KafkaConnectionChecker {
  def main(args: Array[String]): Unit = {
    val kafkaServer = "localhost:9092"

    // Check consumer connection
    val consumerProps = new Properties()
    consumerProps.put("bootstrap.servers", kafkaServer)
    consumerProps.put("group.id", "test-group")
    consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val consumer = new KafkaConsumer[String, String](consumerProps)
    try {
      consumer.subscribe(java.util.Collections.singletonList("test-topic"))
      val records: ConsumerRecords[String, String] = consumer.poll(Duration.ofMillis(1000))
      println("Consumer connection successful")
    } catch {
      case e: Exception =>
        println("Error connecting to Kafka consumer: " + e.getMessage)
    } finally {
      consumer.close()
    }
  }
}

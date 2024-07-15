package scoreboard

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.clients.consumer.{KafkaConsumer, ConsumerRecords, ConsumerConfig}
import java.util.Properties
import java.time.Duration

object KafkaClient {
  val kafkaServer = "localhost:9092"
  
  def produce(topic: String, message: String): Unit = {
    val kafkaProps = new Properties()
    kafkaProps.put("bootstrap.servers", kafkaServer)
    kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    kafkaProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    kafkaProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    
    val producer = new KafkaProducer[String, String](kafkaProps)
    try {
      val key = System.currentTimeMillis().toString
      val record = new ProducerRecord[String, String](topic, key, message)
      producer.send(record)
    } catch {
      case e: Exception =>
        println("Error connecting to Kafka producer: " + e.getMessage)
    } finally {
      producer.close()
    }
  }

  def consume(topic: String): KafkaConsumer[String, String] = {
    val consumerProps = new Properties()
    consumerProps.put("bootstrap.servers", kafkaServer)
    consumerProps.put("group.id", s"$topic-group")
    consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  
    val consumer = new KafkaConsumer[String, String](consumerProps)
    consumer.subscribe(java.util.Collections.singletonList(topic))
    consumer
  }
}

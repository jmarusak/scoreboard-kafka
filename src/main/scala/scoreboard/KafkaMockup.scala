package scoreboard

import java.io.{FileWriter, BufferedWriter}
import java.io.{FileReader, BufferedReader}

object KafkaMockup {
  def produce(topic: String, message: String): Unit = {
    val file = new FileWriter(s"kafka-$topic.json", false)
    val bw = new BufferedWriter(file)
    bw.write(message)
    bw.close()
  }

  def consume(topic: String): String = {
    val file = new FileReader(s"kafka-$topic.json")
    val br = new BufferedReader(file)
    val message = br.readLine()
    br.close()
    message.toString
  }
}

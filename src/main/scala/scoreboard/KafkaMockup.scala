package scoreboard

import java.io.{FileWriter, BufferedWriter}
import scala.util.{Try, Success, Failure}

import scala.io.Source
import scala.util.{Try, Success, Failure}

object KafkaMockup {
  def produce(topic: String, message: String): Unit = {
    val file = new FileWriter(s"kafka-$topic.json", true)
    val bw = new BufferedWriter(file)
    bw.write(message)
    bw.newLine()
    bw.close()
  }

  def consume(topic: String): List[String] = {
    val file = Source.fromFile(s"kafka-$topic.json")
    file.getLines().toList
  }
}

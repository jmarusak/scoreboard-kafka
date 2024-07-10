package scoreboard

import java.io.{FileWriter, BufferedWriter}
import scala.util.{Try, Success, Failure}

import scala.io.Source
import scala.util.{Try, Success, Failure}

object KafkaMockup {
  def produce(message: String): Unit = {
    val file = new FileWriter("kafka.json", true)
    val bw = new BufferedWriter(file)
    bw.write(message)
    bw.newLine()
    bw.close()
  }

  def consume(): Unit = {
    val file = Source.fromFile("kafka.json")
    file.getLines().foreach(println)
  }
}

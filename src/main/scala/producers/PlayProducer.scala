package producers

case class Play(
  court: String,
  teamA: Int,
  teamB: Int)
{
  override def toString: String = {
    s"Play(court $court: $teamA, $teamB)"
  }
}

object PlayProducer {
  def parseArgs(args: Array[String]): Play = {
    if (args.length != 6) {
      println("Usage: producers.PlayProducer --court <number> --teamA <points> --teamB <points>")
      System.exit(1)
    }

    Play("1", 0, 1)
  }

  def main(args: Array[String]): Unit = {
    println("PlayProducer started...")

    val play = parseArgs(args)

    println(s"$play posted.")
  }
}

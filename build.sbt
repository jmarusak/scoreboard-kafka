name := "kafkaland"
version := "1.0"
scalaVersion := "2.13.14"

resolvers += "Akka library repository".at("https://repo.akka.io/maven")

val AkkaVersion = "2.9.3"
val AkkaHttpVersion = "10.6.3"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "org.apache.kafka" % "kafka-clients" % "3.7.0"
)

Compile / mainClass := Some("scoreboard.WebSocketServer")

assembly / assemblyJarName := "Scoreboard.jar"

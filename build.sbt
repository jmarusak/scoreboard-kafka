name := "kafkaland"
version := "1.0"
scalaVersion := "2.12.18"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.7.0"

Compile / scalacOptions ++= Seq(
  "-deprecation"
)

import sbt._

object Dependencies {
  val scalaRequests = "com.lihaoyi" %% "requests" % "0.1.7"
  val scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.1.1"
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  val akka = "com.typesafe.akka"
  val akkaHttp = akka %% "akka-http" % "10.1.8"
  val akkaStream = akka %% "akka-stream" % "2.5.19"
  val json4s = "org.json4s" %% "json4s-native" % "3.6.5"

  val common: Seq[ModuleID] = Seq(
    scalaTest % Test
  )

  val calculator = common
  val api = Seq(
    scalaRequests
    ,scalaXml
    ,json4s
    ,akkaHttp
    ,akkaStream
  ) ++ common
}

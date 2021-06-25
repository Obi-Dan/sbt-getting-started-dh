import sbt._

object Dependencies {
  val scalaRequests = "com.lihaoyi" %% "requests" % "0.1.7"
  val scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.1.1"
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"

  val common: Seq[ModuleID] = Seq(
    scalaTest % Test
  )

  val calculator = common
  val api = Seq(
    scalaRequests,
    scalaXml
  ) ++ common
}

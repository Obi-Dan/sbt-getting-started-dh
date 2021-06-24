name := "sbt-getting-started-dh"

version := "0.1"

scalaVersion := "2.13.6"

//lazy val root = project.in(file(".")).aggregate(calculators)

lazy val calculators = project
  .settings(
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    )
  )

lazy val api = project
  .settings(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "requests" % "0.1.7",
      "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    )
  )
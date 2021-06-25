import com.typesafe.sbt.packager.docker.ExecCmd

name := "sbt-getting-started-dh"
scalaVersion := "2.13.6"

ThisBuild / version := "1.0"
ThisBuild / licenses ++= Seq(
  ("MIT", url("http://opensource.org/licenses/MIT"))
)

publish/skip := true

lazy val calculators = project
  .dependsOn(api)
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
  .settings(
    libraryDependencies ++= Dependencies.calculator,
    dockerCommands := dockerCommands.value.filterNot {
      case ExecCmd("ENTRYPOINT", _) => true
      case _ => false
    },
    dockerCommands ++= Seq(
      ExecCmd("ENTRYPOINT", "/opt/docker/bin/net-worth")
    ),
  )

lazy val api = project
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++= Dependencies.api
  )
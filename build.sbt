ThisBuild / scalaVersion := "3.8.3"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging, DockerPlugin)
  .settings(
    name := "api",
    organization := "com.drumbeatrepo",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-server" % "0.23.34",
      "org.http4s" %% "http4s-dsl" % "0.23.34",
      "org.http4s" %% "http4s-circe" % "0.23.34",
      "io.circe" %% "circe-generic" % "0.14.15",
      "org.typelevel" %% "cats-effect" % "3.7.0",
      "org.eclipse.angus" % "angus-mail" % "2.0.5",
      "org.typelevel" %% "cats-effect-testing-scalatest" % "1.8.0" % Test,
      "org.scalatest" %% "scalatest" % "3.2.20" % Test
    ),
    dockerExposedPorts := Seq(8080),
    dockerBaseImage := "eclipse-temurin:21",
    dockerUpdateLatest := true,
    Docker / packageName := "api",
    Docker / version := "latest"
  )

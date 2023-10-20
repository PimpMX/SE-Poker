ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"
// Noochmal Nachschauen ;) du homo

lazy val root = (project in file("."))
  .settings(
    name := "SE-Poker"
  )

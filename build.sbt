ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "3.3.1"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test"
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"

libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0"
libraryDependencies += "com.google.inject" % "guice" % "4.1.0"

// libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
// libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
// libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.3"
// libraryDependencies += "com.google.inject" % "guice" % "4.1.0"
// libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"
// libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6"
// libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"

lazy val root = (project in file("."))
  .settings(
    name := "SE-Poker"
  )
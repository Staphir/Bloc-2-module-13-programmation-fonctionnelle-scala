ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.8.3"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.20" % Test

lazy val root = (project in file("."))
  .settings(
    name := "Bloc-2-module-13-programmation-fonctionnelle-scala"
  )

import play.sbt.PlayScala

name := """artemisia-ui"""

version := "1.0-SNAPSHOT"

resolvers += Resolver.jcenterRepo

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "2.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "org.webjars" %% "webjars-play" % "2.5.0-4",
  "com.mohiva" %% "play-silhouette" % "4.0.0",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "4.0.0",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "4.0.0",
  "com.mohiva" %% "play-silhouette-persistence" % "4.0.0",
  "com.mohiva" %% "play-silhouette-testkit" % "4.0.0" % "test",
  "com.typesafe.play" %% "play-mailer" % "5.0.0",
  "com.iheart" %% "ficus" % "1.3.4",
  "net.codingwell" %% "scala-guice" % "4.0.1",
  "com.h2database" % "h2" % "1.4.193",
  evolutions
)

lazy val buildReactApp = taskKey[Unit]("build react application")

lazy val cleanReactArtifacts = taskKey[Unit]("clean react artifacts")

cleanReactArtifacts := {
  val publicDir = baseDirectory.value / "public"
  val buildPath: File = baseDirectory.value / "react" / "build"
  publicDir :: buildPath :: Nil foreach  {
    x => IO.delete(x); IO.createDirectory(x)
  }
}

buildReactApp := {
  val rootPath: File = baseDirectory.value / "react"
  val buildPath: File = baseDirectory.value / "react" / "build"
  val publicDirectory: File = baseDirectory.value / "public"
  if (publicDirectory.list().isEmpty)
        Process("npm" :: "run" :: "build" :: Nil, rootPath).!
        IO.copyDirectory(buildPath, publicDirectory, overwrite = true, preserveLastModified = true)
  }

(compile in Compile) <<= (compile in Compile) dependsOn buildReactApp

(clean) <<= (clean) dependsOn cleanReactArtifacts
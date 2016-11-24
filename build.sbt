import play.sbt.PlayScala

name := """artemisia-ui"""

version := "1.0-SNAPSHOT"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.play" %% "play-slick" % "2.0.2"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)


libraryDependencies ++= Seq(
  "com.mohiva" %% "play-silhouette" % "4.0.0",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "4.0.0",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "4.0.0",
  "com.mohiva" %% "play-silhouette-persistence" % "4.0.0",
  "com.mohiva" %% "play-silhouette-testkit" % "4.0.0" % "test"
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
name := "akka-http-docker"

version := "0.1"

scalaVersion := "2.13.6"

val akkaVersion     = "2.6.8"
val akkaHttpVersion = "10.2.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"  % akkaVersion,
  "com.typesafe.akka" %% "akka-http"   % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
)

// Setup for creating docker images:
mainClass in Compile := Some("com.gm.AkkaHttpService")

//dockerBaseImage := "adoptopenjdk/openjdk11:alpine-slim"
//dockerBaseImage := "openjdk:jre"
dockerBaseImage := "openjdk:jre-alpine"

Docker / packageName := "akka-http-dockerized"
dockerAliases ++= Seq(dockerAlias.value.withTag(Option("3.1")))

enablePlugins(AshScriptPlugin)

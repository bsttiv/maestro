val scala3Version = "3.8.4"

lazy val root = project
  .in(file("."))
  .enablePlugins(JavaAppPackaging, DockerPlugin)
  .settings(
    name := "maestro",
    organization := "com.github.bsttiv",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-http" % "1.3.0",
      "org.apache.pekko" %% "pekko-actor-typed" % "1.3.0",
      "org.apache.pekko" %% "pekko-stream" % "1.3.0",

      "org.apache.pekko" %% "pekko-http-testkit" % "1.3.0" % Test,
      "org.apache.pekko" %% "pekko-testkit" % "1.3.0" % Test,

      "com.softwaremill.sttp.tapir" %% "tapir-pekko-http-server" % "1.9.8",
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "1.9.8",
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % "1.9.8", // Documentación OpenAPI automática
      
      "com.github.jwt-scala" %% "jwt-circe" % "9.4.5",
      
      "io.lettuce" % "lettuce-core" % "6.3.1.RELEASE",
      
      "ch.qos.logback" % "logback-classic" % "1.4.14",
      "com.dimafeng" %% "testcontainers-scala-scalatest" % "0.44.0" % "test",
      "com.dimafeng" %% "testcontainers-scala-redis" % "0.44.0" % "test",
      "org.scalatest" %% "scalatest" % "3.2.20" % "test",
      "org.scalatestplus" %% "mockito-5-23" % "3.2.20.0" % "test"
    ),
    
    dockerBaseImage := "eclipse-temurin:21-jre",
    dockerExposedPorts := Seq(8080),
    dockerRepository := Some("bsttiv") 
  )

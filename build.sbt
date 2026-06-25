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
      "com.softwaremill.sttp.tapir" %% "tapir-pekko-http-server" % "1.9.8",
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "1.9.8",
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % "1.9.8", // Documentación OpenAPI automática
      
      "com.github.jwt-scala" %% "jwt-circe" % "9.4.5",
      
      "io.lettuce" % "lettuce-core" % "6.3.1.RELEASE",
      
      "ch.qos.logback" % "logback-classic" % "1.4.14",
      
      "org.scalatest" %% "scalatest" % "3.2.17" % Test
    ),
    
    dockerBaseImage := "eclipse-temurin:21-jre",
    dockerExposedPorts := Seq(8080),
    dockerRepository := Some("bsttiv") 
  )

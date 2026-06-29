package com.github.bsttiv.maestro.presentation

import com.github.bsttiv.maestro.application.SessionManager
import com.github.bsttiv.maestro.infrastructure.{JWTManager, KeystoreManager, RedisRepository}
import com.github.bsttiv.maestro.presentation.endpoints.{MaestroEndpoints, MintEndpoint, RevokeEndpoint, ValidateEndpoint}
import org.apache.pekko.actor.ActorSystem
import sttp.tapir.*
import org.apache.pekko.http.scaladsl.Http
import sttp.tapir.endpoint

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.util.{Failure, Success}

object MainServer {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("maestro-system")
    implicit val ec: ExecutionContextExecutor = system.dispatcher
    val redisRepository = new RedisRepository()
    val keystoreManager = new KeystoreManager
    val jwtManager = new JWTManager(keystoreManager)
    val sessionManager = new SessionManager(redisRepository, jwtManager, ec)
    val baseEndpoint = endpoint.in("api" / "v1" / "maestro")
    val mintEndpoint = new MintEndpoint(baseEndpoint, sessionManager)
    val revokeEndpoint = new RevokeEndpoint(baseEndpoint, sessionManager)
    val validateEndpoint = new ValidateEndpoint(baseEndpoint, sessionManager)
    val maestroEndpoints = MaestroEndpoints(mintEndpoint, revokeEndpoint, validateEndpoint)
    val maestroRoutes = maestroEndpoints.routes(using ec)
    val bindingFuture = Http(system).newServerAt("0.0.0.0", 8080).bind(maestroRoutes)
    bindingFuture.onComplete {
      case Success(binding) =>
        println(s"Maestro Zero-Trust server running on http://localhost:8080/")
      case Failure(ex) =>
        println(s"Fatal error starting the server: ${ex.getMessage}")
        system.terminate()
    }


    Await.result(system.whenTerminated, Duration.Inf)
  }
}
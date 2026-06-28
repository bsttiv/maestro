package com.github.bsttiv.maestro.presentation.endpoints

import com.github.bsttiv.maestro.application.ISessionManager
import org.apache.pekko.http.scaladsl.server.Route
import com.github.bsttiv.maestro.domain.TokenPair
import com.github.bsttiv.maestro.domain.models.MintSessionRequest
import sttp.tapir.*
import sttp.tapir.json.circe.*
import sttp.tapir.endpoint.EndpointType
import sttp.tapir.generic.auto._
import io.circe.generic.auto.*
import sttp.tapir.server.pekkohttp.PekkoHttpServerInterpreter
import org.apache.pekko.http.javadsl.server.directives.RouteAdapter

import scala.concurrent.ExecutionContext

class MintEndpoint(private val baseEndpoint:EndpointType[Unit, Unit, Unit, Unit, Any], private val sessionManager: ISessionManager) extends IMintEndpoint {
  private val mintSessionEndpoint = baseEndpoint
    .post
    .in("mint")
    .in(jsonBody[MintSessionRequest])
    .out(jsonBody[TokenPair])
    .errorOut(stringBody)
    .description("Generates a new session for the specified user and registers him on the database");
  override def mintRoute(using ec: ExecutionContext): Route = {
    PekkoHttpServerInterpreter().toRoute(mintSessionEndpoint.serverLogic(req => {
      sessionManager.generateSessionToken(req)
    }))
  }
}

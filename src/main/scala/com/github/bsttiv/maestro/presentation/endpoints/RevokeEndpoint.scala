package com.github.bsttiv.maestro.presentation.endpoints

import com.github.bsttiv.maestro.application.ISessionManager
import org.apache.pekko.http.scaladsl.server.Route
import com.github.bsttiv.maestro.domain.models.RevocationRequest
import sttp.tapir.*
import sttp.tapir.json.circe.*
import io.circe.generic.auto.*
import org.apache.pekko.http.javadsl.server.directives.RouteAdapter
import sttp.tapir.generic.auto.*
import sttp.tapir.endpoint.EndpointType
import sttp.tapir.server.pekkohttp.PekkoHttpServerInterpreter

import scala.concurrent.ExecutionContext

class RevokeEndpoint(private val baseEndpoint:EndpointType[Unit, Unit, Unit, Unit, Any], private val sessionManager: ISessionManager) extends IRevokeEndpoint {
  private val revokeSessionEndpoint = baseEndpoint
    .post
    .in("revoke")
    .in(jsonBody[RevocationRequest])
    .out(statusCode(sttp.model.StatusCode.NoContent))
    .errorOut(stringBody)
    .description("Revokes a session in the database")
  override def revokeRoute(using ec: ExecutionContext): Route = {
    PekkoHttpServerInterpreter().toRoute(revokeSessionEndpoint.serverLogic(req => {
      sessionManager.revokeSessionToken(req)
    }))
  }
}

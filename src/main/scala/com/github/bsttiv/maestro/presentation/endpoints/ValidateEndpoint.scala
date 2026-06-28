package com.github.bsttiv.maestro.presentation.endpoints

import com.github.bsttiv.maestro.application.ISessionManager
import com.github.bsttiv.maestro.domain.models.{ValidationRequest, ValidationResponse}
import org.apache.pekko.http.scaladsl.server.Route
import sttp.tapir.*
import sttp.tapir.json.circe.*
import io.circe.generic.auto.*
import org.apache.pekko.http.javadsl.server.directives.RouteAdapter
import sttp.tapir.generic.auto.*
import sttp.tapir.endpoint.EndpointType
import sttp.tapir.server.pekkohttp.PekkoHttpServerInterpreter

import scala.concurrent.ExecutionContext

class ValidateEndpoint(private val baseEndpoint:EndpointType[Unit, Unit, Unit, Unit, Any], private val sessionManager: ISessionManager) extends IValidateEndpoint {
  private val validateSessionEndpoint = baseEndpoint
    .post
    .in("validate")
    .in(jsonBody[ValidationRequest])
    .out(jsonBody[ValidationResponse])
    .errorOut(stringBody)
    .description("Verifies the session token's signature, TTL and revocation state")
  override def validateRoute(using ec: ExecutionContext): Route = {
    PekkoHttpServerInterpreter().toRoute(validateSessionEndpoint.serverLogic(req => {
      sessionManager.validateSessionToken(req)
    }))
  }
}

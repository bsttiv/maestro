package com.github.bsttiv.maestro.presentation.endpoints

import cats.effect.IO
import cats.effect.std.Dispatcher
import org.apache.pekko.http.javadsl.server.Route
import sttp.tapir.endpoint.EndpointType
import com.github.bsttiv.maestro.domain.TokenPair
import com.github.bsttiv.maestro.presentation.endpoints.models.{MintSessionRequest, RevocationRequest, ValidationRequest}
import sttp.tapir.*
import sttp.tapir.json.circe.*
import io.circe.generic.auto.*
import sttp.tapir.server.pekkohttp.PekkoHttpServerInterpreter

import scala.concurrent.ExecutionContext

class MintEndpoint(private val baseEndpoint:EndpointType[Unit, Unit, Unit, Unit, Any]) extends IMintEndpoint {
  private val mintSessionEndpoint = baseEndpoint
    .post
    .in("mint")
    .in(jsonBody[MintSessionRequest])
    .out(jsonBody[TokenPair])
    .errorOut(stringBody)
    .description("Generates a new session for the specified user and registers him on the database");
  override def mintRoute(using Dispatcher[IO], ExecutionContext): Route = {
    // PekkoHttpServerInterpreter().toRoute(mintSessionEndpoint.serverLogic())
  }
}

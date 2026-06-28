package com.github.bsttiv.maestro.presentation.endpoints

import com.github.bsttiv.maestro.domain.TokenPair
import com.github.bsttiv.maestro.presentation.endpoints.models.{MintSessionRequest, RevocationRequest, ValidationRequest}
import sttp.tapir.*
import sttp.tapir.json.circe.*
import io.circe.generic.auto.*

object MaestroEndpointsMock {
  private val baseEndpoint = endpoint.in("api" / "v1" / "maestro")
  val mintSessionEndpoint = baseEndpoint
    .post
    .in("mint")
    .in(jsonBody[MintSessionRequest])
    .out(jsonBody[TokenPair])
    .errorOut(stringBody)
    .description("Generates a new session for the specified user and registers him on the database");
  val validateSessionEndpoint = baseEndpoint
    .post
    .in("validate")
    .in(jsonBody[ValidationRequest])
    .in(jsonBody[ValidationResponse])
    .errorOut(stringBody)
    .description("Verifies the session token's signature, TTL and revocation state")
  val revokeSessionEndpoint = baseEndpoint
    .post
    .in("revoke")
    .in(jsonBody[RevocationRequest])
    .out(statusCode(sttp.model.StatusCode.NoContent))
    .errorOut(stringBody)
    .description("Revokes a session in the database")
}

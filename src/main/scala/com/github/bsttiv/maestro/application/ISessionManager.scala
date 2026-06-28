package com.github.bsttiv.maestro.application

import com.github.bsttiv.maestro.domain.TokenPair
import com.github.bsttiv.maestro.domain.models.{MintSessionRequest, RevocationRequest, ValidationRequest, ValidationResponse}

import scala.concurrent.Future

trait ISessionManager {
  def generateSessionToken(req: MintSessionRequest): Future[Either[String, TokenPair]];
  def revokeSessionToken(req: RevocationRequest): Future[Either[String, Unit]];
  def validateSessionToken(req: ValidationRequest): Future[Either[String, ValidationResponse]];
}

package com.github.bsttiv.maestro.application

import com.github.bsttiv.maestro.domain.TokenPair
import com.github.bsttiv.maestro.domain.models.{MintSessionRequest, RevocationRequest, ValidationRequest, ValidationResponse}
import com.github.bsttiv.maestro.infrastructure.{IRepository, JWTManager, KeystoreManager}

import scala.concurrent.Future

class SessionManager(private val repository: IRepository) extends ISessionManager {
  private val jwtManager = new JWTManager(new KeystoreManager);

  override def generateSessionToken(req: MintSessionRequest): Future[Either[String, TokenPair]] = ???

  override def revokeSessionToken(req: RevocationRequest): Future[Either[String, Unit]] = ???

  override def validateSessionToken(req: ValidationRequest): Future[Either[String, ValidationResponse]] = ???
}

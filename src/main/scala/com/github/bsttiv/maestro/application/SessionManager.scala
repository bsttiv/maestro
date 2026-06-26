package com.github.bsttiv.maestro.application

import com.github.bsttiv.maestro.infrastructure.{JWTManager, KeystoreManager}

class SessionManager(private val repository: IRepository) extends ISessionManager {
  private val jwtManager = new JWTManager;

  override def generateSessionToken(): Unit = ???

  override def revokeSessionToken(): Unit = ???

  override def verifySessionToken(): Unit = ???
}

package com.github.bsttiv.maestro.infrastructure

import com.github.bsttiv.maestro.domain.TokenClaims

import java.security.PublicKey

trait IJWTManager {
  def verifySignature(token: String, publicKey: PublicKey): Boolean;
  def generateToken(claims: TokenClaims): String;
  def decodeToken(token: String): TokenClaims;
}

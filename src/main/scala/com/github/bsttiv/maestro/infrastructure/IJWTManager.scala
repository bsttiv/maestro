package com.github.bsttiv.maestro.infrastructure

import com.github.bsttiv.maestro.domain.TokenClaimsAccess
import io.circe.Encoder

import java.security.PublicKey
import scala.util.Try

trait IJWTManager {
  def tokenExpired(token: TokenClaimsAccess): Boolean;
  def tokenFingerprint(token: TokenClaimsAccess, ctx: String): Boolean;
  def generateToken[T: Encoder](claims: T): String;
  def decodeToken(token: String): Try[TokenClaimsAccess];
}

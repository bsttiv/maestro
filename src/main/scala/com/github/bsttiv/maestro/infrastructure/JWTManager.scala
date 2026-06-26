package com.github.bsttiv.maestro.infrastructure

import com.github.bsttiv.maestro.domain.TokenClaims
import pdi.jwt.{JwtAlgorithm, JwtCirce, JwtClaim}

import java.security.PublicKey
import io.circe.syntax.*

class JWTManager extends IJWTManager {
  private val keystoreManager = new KeystoreManager;

  override def verifySignature(token: String, publicKey: PublicKey): Boolean = {
    JwtCirce.isValid(token, publicKey, Seq(JwtAlgorithm.EdDSA))
  }

  override def generateToken(claims: TokenClaims): String = {
    val key = keystoreManager.getPrivateKey;
    val algo = JwtAlgorithm.EdDSA;
    val json = claims.asJson.noSpaces;
    val jwtClaim = JwtClaim(content=json);
    JwtCirce.encode(jwtClaim, key, algo);
  }

  override def decodeToken(token: String): TokenClaims = {
    val key = keystoreManager.getPrivateKey;
    val algo = JwtAlgorithm.EdDSA;
    JwtCirce.decode(token, key, Seq(JwtAlgorithm.EdDSA));
  }
}

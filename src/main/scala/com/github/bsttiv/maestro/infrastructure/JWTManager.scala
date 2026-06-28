package com.github.bsttiv.maestro.infrastructure

import com.github.bsttiv.maestro.domain.TokenClaimsAccess
import pdi.jwt.{JwtAlgorithm, JwtCirce, JwtClaim}
import io.circe.Encoder

import java.security.PublicKey
import java.time.Instant
import io.circe.syntax.*
import io.circe.parser.decode
import scala.util.Try

class JWTManager(private val keystoreManager: IKeystoreManager) extends IJWTManager {

  override def tokenFingerprint(token: TokenClaimsAccess, ctx: String): Boolean = token.ctx == ctx

  override def tokenExpired(token: TokenClaimsAccess): Boolean = Instant.now().getEpochSecond >= token.exp

  override def generateToken[T: Encoder](claims: T): String = {
    val key = keystoreManager.getPrivateKey;
    val algo = JwtAlgorithm.EdDSA;
    val json = claims.asJson.noSpaces;
    val jwtClaim = JwtClaim(content=json);
    JwtCirce.encode(jwtClaim, key, algo);
  }

  override def decodeToken(token: String): Try[TokenClaimsAccess] = {
    val key = keystoreManager.getPublicKey;
    val algo = JwtAlgorithm.EdDSA;
    JwtCirce.decode(token, key, Seq(JwtAlgorithm.EdDSA)).flatMap(claim => decode[TokenClaimsAccess](claim.content).toTry);
  }
}

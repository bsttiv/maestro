package com.github.bsttiv.maestro.domain

import io.circe.Encoder
import io.circe.Decoder

case class TokenClaimsAccess(typ:String = "Bearer", ctx: String, iss: String, sub: String, iat: Long, exp:Long, jti: String) extends TokenClaims derives Encoder.AsObject, Decoder;

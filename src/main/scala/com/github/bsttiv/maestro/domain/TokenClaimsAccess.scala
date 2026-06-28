package com.github.bsttiv.maestro.domain

import io.circe.Encoder
import io.circe.Decoder

case class TokenClaimsAcces(typ:String, iss: String, sub: String, iat: Long, expTime:Long, jti: String, ctx: String) derives Encoder.AsObject, Decoder;

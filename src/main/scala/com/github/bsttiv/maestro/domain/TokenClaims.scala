package com.github.bsttiv.maestro.domain

import io.circe.Encoder
import io.circe.Decoder

case class TokenClaims(iss: String, sub: String, iat: Int, expTime:String, jti: String, ctx: String) derives Encoder.AsObject, Decoder;

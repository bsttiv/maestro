package com.github.bsttiv.maestro.domain

import io.circe.Encoder

case class TokenClaims(iss: String, sub: String, iat: int, expTime:String, jti: String, ctx: String) derives Encoder.AsObject;

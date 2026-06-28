package com.github.bsttiv.maestro.domain

import io.circe.{Decoder, Encoder}

case class TokenClaimsRefresh(typ:String = "Refresh", iss: String, sub:String, jti:String, iat:Long, exp:Long) extends TokenClaims derives Encoder.AsObject, Decoder

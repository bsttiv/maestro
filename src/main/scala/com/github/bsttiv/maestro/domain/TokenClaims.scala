package com.github.bsttiv.maestro.domain

case class TokenClaims(iss: String, sub: String, iat: int, expTime:String, jti: String, ctx: String);

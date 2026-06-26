package com.github.bsttiv.maestro.domain

import pdi.jwt.JwtCirce

case class TokenPair(accessToken: String, refreshToken: String);

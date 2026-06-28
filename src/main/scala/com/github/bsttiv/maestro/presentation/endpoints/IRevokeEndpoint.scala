package com.github.bsttiv.maestro.presentation.endpoints

import org.apache.pekko.http.javadsl.server.Route

import scala.concurrent.ExecutionContext

trait IRevokeEndpoint {
  def revokeRoute(using ec: ExecutionContext): Route
}

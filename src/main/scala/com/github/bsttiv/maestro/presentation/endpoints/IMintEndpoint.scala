package com.github.bsttiv.maestro.presentation.endpoints

import org.apache.pekko.http.javadsl.server.Route

import scala.concurrent.ExecutionContext

trait IMintEndpoint {
  def mintRoute(using ec: ExecutionContext): Route
}

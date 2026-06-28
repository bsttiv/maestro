package com.github.bsttiv.maestro.presentation.endpoints

import org.apache.pekko.http.scaladsl.server.Route

import scala.concurrent.ExecutionContext

trait IMintEndpoint {
  def mintRoute(using ec: ExecutionContext): Route
}

package com.github.bsttiv.maestro.presentation.endpoints

import org.apache.pekko.http.scaladsl.server.Route

import scala.concurrent.ExecutionContext

trait IValidateEndpoint {
  def validateRoute(using ec: ExecutionContext): Route
}

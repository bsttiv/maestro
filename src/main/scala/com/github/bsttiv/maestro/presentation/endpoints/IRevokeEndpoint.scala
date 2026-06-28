package com.github.bsttiv.maestro.presentation.endpoints

import scala.concurrent.ExecutionContext

trait IRevokeEndpoint {
  def revokeRoute(using ec: ExecutionContext): Route
}

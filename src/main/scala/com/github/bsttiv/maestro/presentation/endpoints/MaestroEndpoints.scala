package com.github.bsttiv.maestro.presentation.endpoints

import org.apache.pekko.http.scaladsl.server.Route
import org.apache.pekko.http.scaladsl.server.Directives.concat

import scala.concurrent.ExecutionContext

class MaestroEndpoints(private val mintEndpoint: IMintEndpoint, private val revokeEndpoint: IRevokeEndpoint, private val validateEndpoint: IValidateEndpoint){
  def routes(using ec: ExecutionContext): Route = {
    concat(
      mintEndpoint.mintRoute,
      revokeEndpoint.revokeRoute,
      validateEndpoint.validateRoute)
  }
}

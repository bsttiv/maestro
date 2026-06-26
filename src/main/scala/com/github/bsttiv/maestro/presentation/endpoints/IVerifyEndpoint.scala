package com.github.bsttiv.maestro.presentation.endpoints

import cats.effect.IO
import cats.effect.std.Dispatcher
import org.apache.pekko.http.javadsl.server.Route

import scala.concurrent.ExecutionContext

trait IVerifyEndpoint {
  def verifyRoute(using Dispatcher[IO], ExecutionContext): Route
}

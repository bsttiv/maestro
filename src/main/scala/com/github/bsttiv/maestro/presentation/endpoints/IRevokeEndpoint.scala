package com.github.bsttiv.maestro.presentation.endpoints

import cats.effect.IO
import cats.effect.std.Dispatcher

import scala.concurrent.ExecutionContext

trait IRevokeEndpoint {
  def revokeRoute(using Dispatcher[IO], ExecutionContext): Route
}

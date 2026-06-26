package com.github.bsttiv.maestro.presentation.endpoints

import cats.effect.std.Dispatcher
import cats.effect.*
import org.apache.pekko.http.javadsl.server.Route

import scala.concurrent.ExecutionContext

trait IMintEndpoint {
  def mintRoute(using Dispatcher[IO], ExecutionContext): Route
}

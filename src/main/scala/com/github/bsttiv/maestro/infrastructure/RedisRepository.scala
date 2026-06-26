package com.github.bsttiv.maestro.infrastructure

import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.async.RedisAsyncCommands
import io.lettuce.core.api.sync.RedisCommands
import io.lettuce.core.{RedisClient, RedisURI}

class RedisRepository private () extends IRepository{
  private val uri: RedisURI = RedisURI("localhost", 6379)
  private val client: RedisClient = RedisClient(uri);
  private val connection: StatefulRedisConnection = client.connect();
  private val commands: RedisAsyncCommands = connection.async();
}

object RedisRepository {
  private lazy val instance: RedisRepository = new RedisRepository()
  def getInstance(): RedisRepository = instance;
}

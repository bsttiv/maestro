package com.github.bsttiv.maestro.infrastructure

import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.async.RedisAsyncCommands
import io.lettuce.core.api.sync.RedisCommands
import io.lettuce.core.{RedisClient, RedisFuture, RedisURI}

class RedisRepository private () extends IRepository{
  private val uri: RedisURI = RedisURI("localhost", 6379)
  private val client: RedisClient = RedisClient(uri);
  private val connection: StatefulRedisConnection = client.connect();
  private val commands: RedisAsyncCommands = connection.async();

  override def saveRecord(uuid: String, time: Int): RedisFuture[String] = {
    commands.setex(f"session:$uuid", time, "Active");
  };

  override def revokeRecord(uuid: String): RedisFuture[String] = {
    commands.set(f"session:$uuid", "Revoked");
  };

  override def searchRecord(uuid: String): RedisFuture[String] = {
    commands.get(f"session:$uuid");
  };
}

object RedisRepository {
  private lazy val instance: RedisRepository = new RedisRepository()
  def getInstance(): RedisRepository = instance;
}

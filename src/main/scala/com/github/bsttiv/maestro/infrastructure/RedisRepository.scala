package com.github.bsttiv.maestro.infrastructure

import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.async.RedisAsyncCommands
import io.lettuce.core.api.sync.RedisCommands
import io.lettuce.core.{RedisClient, RedisFuture, RedisURI}

import scala.jdk.FutureConverters.*
import scala.concurrent.{ExecutionContext, Future}

class RedisRepository (host: String = "localhost", port: Int = 6379) extends IRepository {
  private val uri: RedisURI = RedisURI.create("localhost", 6379);
  private val client: RedisClient = RedisClient.create(uri);
  private val connection: StatefulRedisConnection[String, String] = client.connect();
  private val commands: RedisAsyncCommands[String, String] = connection.async();

  override def saveRecord(uuid: String, time: Long): Future[String] = {
    commands.setex(f"session:$uuid", time, "Active").asScala;
  };

  override def revokeRecord(uuid: String): Future[String] = {
    commands.set(f"session:$uuid", "Revoked").asScala;
  };

  override def searchRecord(implicit ec: ExecutionContext, uuid: String): Future[Option[String]] = {
    commands.get(f"session:$uuid").asScala.map(v => Option(v));
  };
}
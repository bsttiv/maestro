package com.github.bsttiv.maestro.infrastructure

import io.lettuce.core.RedisFuture

trait IRepository {
  def searchRecord(uuid:String): RedisFuture[String];
  def saveRecord(uuid:String, time:Int): RedisFuture[String];
  def revokeRecord(uuid:String): RedisFuture[String];
}

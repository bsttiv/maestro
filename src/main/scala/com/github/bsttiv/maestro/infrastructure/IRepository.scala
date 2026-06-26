package com.github.bsttiv.maestro.infrastructure

trait IRepository {
  def searchRecord(uuid:String): Unit;
  def saveRecord(uuid:String, time:Int): Unit;
  def revokeRecord(uuid:String): Unit;
}

package com.github.bsttiv.maestro.infrastructure

import scala.concurrent.{ExecutionContext, Future}

trait IRepository {
  def searchRecord(implicit ec: ExecutionContext, uuid:String): Future[Option[String]];
  def saveRecord(uuid:String, time:Long): Future[String];
  def revokeRecord(uuid:String): Future[String];
}

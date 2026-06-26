package com.github.bsttiv.maestro.infrastructure

trait IKeystoreManager {
  def changeKey(): Unit;
  def deleteOldKey(): Unit;
  def verifySignature(signature:String): Boolean;
}

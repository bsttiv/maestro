package com.github.bsttiv.maestro.infrastructure

class KeystoreManager extends IKeystoreManager {
  override def changeKey(): Unit = ???;

  override def deleteOldKey(): Unit = ???;
  
  override def verifySignature(signature: String): Boolean = ???;
}

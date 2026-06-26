package com.github.bsttiv.maestro.infrastructure

import java.security.{KeyPair, PrivateKey, PublicKey}

trait IKeystoreManager {
  def changeKey(): Unit;
  def getPrivateKey: PrivateKey;
  def getPublicKey: PublicKey;
  def getOldKeyPair: KeyPair;
}

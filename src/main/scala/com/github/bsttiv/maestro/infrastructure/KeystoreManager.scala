package com.github.bsttiv.maestro.infrastructure

import com.github.bsttiv.maestro.infrastructure.io.KeyReader

import java.security.{KeyFactory, KeyPair, KeyPairGenerator, PrivateKey, Signature}
import scala.io.Source

class KeystoreManager(private val reader: KeyReader) extends IKeystoreManager {
  // TODO: Save keys in Redis in case server gets restarted
  private val keyGenerator = KeyPairGenerator.getInstance("Ed25519")
  private var currentKeyPair = keyGenerator.generateKeyPair();
  private var oldKeyPair = currentKeyPair;
  override def changeKey(): Unit = {
    val newPair = keyGenerator.generateKeyPair();
    oldKeyPair = currentKeyPair;
    currentKeyPair = newPair;
  };
  def getPrivateKey: PrivateKey = currentKeyPair.getPrivate;
}

package com.github.bsttiv.maestro.infrastructure

import java.security.{KeyFactory, KeyPair, KeyPairGenerator, PrivateKey, PublicKey, Signature}
import java.util.concurrent.atomic.AtomicReference
import scala.io.Source

class KeystoreManager extends IKeystoreManager {
  // TODO: Save keys in Redis in case server gets restarted
  private val keyGenerator = KeyPairGenerator.getInstance("Ed25519")
  private val currentKeyPair = AtomicReference[KeyPair](keyGenerator.generateKeyPair());
  private val oldKeyPair = AtomicReference[KeyPair](currentKeyPair.get());
  override def changeKey(): Unit = {
    val newPair = keyGenerator.generateKeyPair();
    oldKeyPair.set(currentKeyPair.get());
    currentKeyPair.set(newPair);
  };
  def getPrivateKey: PrivateKey = currentKeyPair.get().getPrivate;
  def getPublicKey: PublicKey = currentKeyPair.get().getPublic;
  def getOldKeyPair: KeyPair = oldKeyPair.get();
}

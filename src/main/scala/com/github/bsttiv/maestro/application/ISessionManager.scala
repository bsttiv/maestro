package com.github.bsttiv.maestro.application

trait ISessionManager {
  def generateSessionToken(): Unit;
  def revokeSessionToken(): Unit;
  def verifySessionToken(): Unit;
}

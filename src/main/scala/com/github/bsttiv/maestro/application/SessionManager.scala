package com.github.bsttiv.maestro.application

import com.github.bsttiv.maestro.domain.{TokenClaimsAccess, TokenClaimsRefresh, TokenPair}
import com.github.bsttiv.maestro.domain.models.{MintSessionRequest, RevocationRequest, ValidationRequest, ValidationResponse}
import com.github.bsttiv.maestro.infrastructure.{IRepository, JWTManager, KeystoreManager}

import scala.util.{Failure, Success}
import java.util.UUID
import java.time.Instant
import scala.concurrent.{ExecutionContext, Future}


class SessionManager(private val repository: IRepository, private val jwtManager: JWTManager, implicit private val ec: ExecutionContext) extends ISessionManager {
  private val ACCESS_TTL_SECONDS = 15 * 60
  private val REFRESH_TTL_SECONDS = 7 * 24 * 3600
  override def generateSessionToken(req: MintSessionRequest): Future[Either[String, TokenPair]] = {
    val jti = UUID.randomUUID().toString
    val result = repository.saveRecord(jti, REFRESH_TTL_SECONDS)
    result.map(result => {
      if (result.strip() == "OK"){
        val timestamp = Instant.now().getEpochSecond
        val access = jwtManager.generateToken[TokenClaimsAccess](TokenClaimsAccess(iss="maestro", sub=s"sub_${req.userId}", iat=timestamp, exp=timestamp+ACCESS_TTL_SECONDS, jti=jti, ctx=req.context))
        val refresh = jwtManager.generateToken[TokenClaimsRefresh](TokenClaimsRefresh(iss="maestro", sub=s"sub_${req.userId}", iat=timestamp, exp=timestamp+REFRESH_TTL_SECONDS, jti=jti))
        Right(TokenPair(access, refresh))
      } else {
        Left("Error interacting with the database")
      }
    }).recover {
      case e: Exception => Left("Error interacting with the database")
    }
  }

  override def revokeSessionToken(req: RevocationRequest): Future[Either[String, Unit]] = {
    val token = jwtManager.decodeToken(req.token)
    token match{
      case Success(r) => {
        val result = repository.revokeRecord(r.jti)
        result.map(r => {
          if (r.strip() == "OK"){
            Right(())
          } else{
            Left("Error interacting with the database")
          }
        }).recover{
          case e: Exception => Left("Error interacting with the database")
        }
      }
      case Failure(e) => {
        Future.successful(Left("Token signature does not match any used keys"))
      }
    }
  }

  override def validateSessionToken(req: ValidationRequest): Future[Either[String, ValidationResponse]] = {
    val token = jwtManager.decodeToken(req.token)
    token match {
      case Success(r) => {
        val isExpired = jwtManager.tokenExpired(r)
        if (isExpired)  Future.successful(Left("Token has expired"))
        else {
          println(r)
          println(req.fingerprint)
          val correctFingerprint = jwtManager.tokenFingerprint(r, req.fingerprint)
          if (!correctFingerprint) Future.successful(Left("Token does not match the current fingerprint"))
          else {
            val state = repository.searchRecord(using ec, r.jti)
            state.map {
              case Some("Revoked") => Left("Token has been revoked")
              case None => Left("Token is not in database")
              case _ => Right(ValidationResponse())
            }.recover {
              case e: Exception => Left("Error interacting with the database")
            }
          }
        }
      }
      case Failure(e) => {
        Future.successful(Left("Token signature does not match any used keys"))
      }
    }
  }
}

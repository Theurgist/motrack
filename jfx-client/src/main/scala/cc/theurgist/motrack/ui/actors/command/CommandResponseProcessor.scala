package cc.theurgist.motrack.ui.actors.command

import akka.actor.Actor.Receive
import akka.actor.ActorContext
import akka.event.LoggingAdapter
import akka.http.scaladsl.model.ResponseEntity
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.Materializer
import cc.theurgist.motrack.lib.config.CommonConfig
import cc.theurgist.motrack.lib.dto.{Red, ServerStatus}
import cc.theurgist.motrack.lib.model.security.session.SessionId
import cc.theurgist.motrack.lib.model.security.user.SafeUser
import cc.theurgist.motrack.ui.actors.{LoginAttempt, LoginResult, UpdateServerStatus}
import cc.theurgist.motrack.ui.config.ClientConfig
import cc.theurgist.motrack.ui.network.AkkaHttpRequester.ReqResponse
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

import scala.util.{Success, Try}

/**
  * Encapsulates logic for actor's http responses processing via actor's embeddable partial function
  */
trait CommandResponseProcessor {
  protected def log: LoggingAdapter
  protected implicit def m: Materializer

  private def decode[T](re: ResponseEntity)(implicit um: Unmarshaller[ResponseEntity, T]): Option[Try[T]] =
    Unmarshal(re).to[T].value

  /**
    * Process queried http responses
    */
  def response(implicit ac: ActorContext): Receive = {
    case ReqResponse(cmd, initiator, outcome) =>
      cmd match {
        case UpdateServerStatus() =>
          outcome match {
            case Right(r) =>
              decode[ServerStatus](r.entity) match {
                case Some(Success(status)) =>
                  log.debug(s"PING RECV: $status")
                  initiator ! status
                case e => log.warning(s"PING ERR: $e")
              }

            case Left(e) =>
              initiator ! ServerStatus(Red, "Server unreachable")
          }

        case LoginAttempt(_) =>
          outcome match {
            case Right(r) =>
              decode[(Long, SafeUser)](r.entity) match {
                case Some(Success((sid, u))) =>
                  initiator ! LoginResult(Right((new SessionId(sid), u)))
                case e =>
                  initiator ! LoginResult(Left(e.toString))
              }
            case Left(e) =>
          }

        case unmatched =>
          log.error(s"Unmatched http response for command $cmd: $outcome")
      }

  }
}

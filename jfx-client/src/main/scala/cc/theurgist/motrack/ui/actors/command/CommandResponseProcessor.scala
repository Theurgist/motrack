package cc.theurgist.motrack.ui.actors.command

import akka.actor.Actor.Receive
import akka.actor.ActorContext
import akka.event.LoggingAdapter
import akka.http.scaladsl.model.ResponseEntity
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.Materializer
import cc.theurgist.motrack.lib.dto.{Red, ServerStatus}
import cc.theurgist.motrack.lib.messages.{LoginAttempt, LoginResult, ServersideError, UpdateServerStatus}
import cc.theurgist.motrack.ui.network.AkkaHttpRequester.{ReqFailure, ReqResponse}
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

    case ReqResponse(cmd, initiator, r) =>
      cmd match {
        case UpdateServerStatus() =>
          decode[ServerStatus](r.entity) match {
            case Some(Success(status)) =>
              log.debug(s"PING RECV: $status")
              initiator ! status
            case e => log.warning(s"PING ERR: $e")
          }

        case LoginAttempt(_) =>
          decode[LoginResult](r.entity) match {
            case Some(Success(loginResult)) => initiator ! LoginResult(loginResult.r)
          }

        case _ =>
          val msg = s"Unmatched http response for command $cmd: $r"
          log.error(msg)
          initiator ! ServersideError(msg)
      }

    case ReqFailure(cmd, initiator, error) =>
      initiator ! ServerStatus(Red, "Server unreachable", error.toString)

  }
}

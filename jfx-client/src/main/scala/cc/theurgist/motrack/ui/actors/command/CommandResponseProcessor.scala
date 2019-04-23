package cc.theurgist.motrack.ui.actors.command

import akka.actor.Actor.Receive
import akka.actor.ActorContext
import akka.event.LoggingAdapter
import akka.http.scaladsl.model.ResponseEntity
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.Materializer
import cc.theurgist.motrack.lib.dto.ServerStatus
import cc.theurgist.motrack.ui.actors.UpdateServerStatus
import cc.theurgist.motrack.ui.network.AkkaHttpRequester.CommandHttpResponse
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
    case CommandHttpResponse(cmd, r, initiator) =>
      cmd match {
        case UpdateServerStatus() =>
          decode[ServerStatus](r.entity) match {
            case Some(Success(status)) =>
              log.debug(s"PING RECV: $status")
              initiator ! status
            case e => log.warning(s"PING ERR: $e")
          }
        case unmatched =>
          log.error(s"unmatched http response for command $cmd: $r")
      }

  }
}

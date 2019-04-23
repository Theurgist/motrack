package cc.theurgist.motrack.ui.actors.command

import akka.actor.{Actor, PoisonPill, Props}
import akka.event.{Logging, LoggingReceive}
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.{HttpEntity, MediaTypes, RequestEntity, ResponseEntity}
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.Materializer
import cc.theurgist.motrack.lib.dto.ServerStatus
import cc.theurgist.motrack.lib.model.security.session.SessionId
import cc.theurgist.motrack.lib.model.security.user.SafeUser
import cc.theurgist.motrack.ui.actors.{Exit, LoginAttempt, Terminate, UpdateServerStatus}
import cc.theurgist.motrack.ui.network.AkkaHttpRequester.CommandHttpResponse
import cc.theurgist.motrack.ui.network.{AkkaHttpRequester, Req}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.duration._
import scala.language.{implicitConversions, postfixOps}
import scala.util.{Success, Try}

case class SessionedMessage[T](
    user: SafeUser,
    sessionId: SessionId,
    data: T
)

class CommandActor(implicit materializer: Materializer) extends Actor {
  private val log                                = Logging(context.system, this)
  private val http                               = new AkkaHttpRequester(context)
  implicit def entity(json: Json): RequestEntity = HttpEntity(MediaTypes.`application/json`, json.toString())

  private def decode[T](re: ResponseEntity)(implicit um: Unmarshaller[ResponseEntity, T]): Option[Try[T]] = Unmarshal(re).to[T].value

  override def receive: Receive = LoggingReceive {
    // Actor identification pipeline
//    case 'listActors => context.actorSelection("/user/*") ! Identify()
//    case path: ActorPath => context.actorSelection(path / "*") ! Identify()
//    case ActorIdentity(_, Some(ref)) => { log.info("Got actor " + ref.path.toString); self ! ref.path }

    case m: UpdateServerStatus => http ! Req(GET, "info/status", m)

    case la: LoginAttempt => http ! Req(POST, "security/do-login", la, la.data.asJson)

    case Exit =>
      log.info("Exiting application")
      if (sender() != self) sender() ! PoisonPill
      context.system.scheduler.scheduleOnce(1 seconds, self, Terminate)(context.dispatcher)
    case Terminate => context.system.terminate()

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

    case wrongMsg =>
      log.error(s"CMD got unrecognized message: $wrongMsg; ${wrongMsg.getClass}")
  }

}

object CommandActor {
  def props(implicit m: Materializer): Props = Props(new CommandActor())
}

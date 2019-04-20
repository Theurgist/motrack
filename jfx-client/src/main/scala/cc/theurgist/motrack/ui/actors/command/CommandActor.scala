package cc.theurgist.motrack.ui.actors.command

import akka.actor.{Actor, PoisonPill, Props}
import akka.event.{Logging, LoggingReceive}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import cc.theurgist.motrack.lib.dto.ServerStatus
import cc.theurgist.motrack.ui.actors.{Exit, UpdateServerStatus}
import cc.theurgist.motrack.ui.network.{CommandHttpResponse, Requester}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

import scala.util.Success

class CommandActor(implicit materializer: Materializer) extends Actor {
  private val log                         = Logging(context.system, this)

  private val requester = new Requester(context)

  override def receive: Receive = LoggingReceive {
//    case 'listActors =>
//      context.actorSelection("/user/*") ! Identify()
//
//    case path: ActorPath =>
//      context.actorSelection(path / "*") ! Identify()
//
//    case ActorIdentity(_, Some(ref)) =>
//      log.info("Got actor " + ref.path.toString)
//      self ! ref.path


    case m: UpdateServerStatus =>
      log.info("Pinging..")
      val s = sender()
      requester.reqForSelf("info/status", m)
      sender()

    case Exit =>
      log.info("Exiting application")
      self ! PoisonPill

    case CommandHttpResponse(cmd, r, i) =>
      cmd match {
        case UpdateServerStatus() =>
          Unmarshal(r.entity).to[ServerStatus].value match {
            case Some(Success(status)) =>
              val s = sender()
              log.info(s"PING RECV: $status")
              i ! status
            case e =>
              log.info(s"PING ERR: $e")
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

package cc.theurgist.motrack.ui.actors.command

import akka.actor.{Actor, ActorIdentity, ActorPath, ActorRef, ActorSystem, Identify, PoisonPill, Props}
import akka.event.{Logging, LoggingReceive}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, Materializer}
import cc.theurgist.motrack.lib.dto.ServerStatus
import cc.theurgist.motrack.ui.network.{CommandHttpResponse, Requester}
import cc.theurgist.motrack.ui.actors.gui.GuiActor
import cc.theurgist.motrack.ui.actors.{Exit, UpdateServerStatus}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

import scala.util.Success

class CommandActor(implicit materializer: Materializer) extends Actor {
  private val log                         = Logging(context.system, this)

  private val requester = new Requester

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
      requester.reqForSelf("info/status", m)

    case Exit =>
      log.info("Exiting application")
      self ! PoisonPill

    case CommandHttpResponse(cmd, r) =>
      cmd match {
        case UpdateServerStatus(sender) =>
          Unmarshal(r.entity).to[ServerStatus].value match {
            case Some(Success(status)) =>
              log.info(s"PING RECV: $status")
              sender ! status
            case e =>
              log.info(s"PING ERR: $e")
          }

        case unmatched => log.error(s"unmatched http response for command $cmd: $r")
      }

    case wrongMsg => log.error(s"got unrecognized message: $wrongMsg")
  }

}

object CommandActor {
  def props(implicit m: Materializer): Props = Props(new CommandActor())
}

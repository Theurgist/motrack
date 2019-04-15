package cc.theurgist.motrack.ui.actors.network

import akka.actor.{Actor, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, Materializer}
import cc.theurgist.motrack.lib.dto.ServerStatus
import cc.theurgist.motrack.ui.actors.command.{CmdMessage, UpdateServerStatus}
import cc.theurgist.motrack.ui.config.ClientConfig
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.Success

class NetworkActor extends Actor {
  implicit val materializer: Materializer                 = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = context.dispatcher
  import akka.pattern.pipe

  private val log = Logging(context.system, this)
  private val http = Http(context.system)

  override def preStart(): Unit = {
    self ! UpdateServerStatus
  }

  override def receive: Receive = {
    case UpdateServerStatus =>
      srvreq("info/status").map(r => CommandHttpResponse(UpdateServerStatus(), r)).pipeTo(self)



    case CommandHttpResponse(cmd, r) =>
      cmd match {
        case UpdateServerStatus() =>
          Unmarshal(r.entity).to[ServerStatus].value match {
            case Some(Success(status)) => log.info(s"PING RECV: $status")
            case e => log.info(s"PING ERR: $e")
          }


        case unmatched => log.error(s"unmatched http response for command $cmd: $r")
      }

    case wrongMsg => log.error(s"got unrecognized message: $wrongMsg")
  }

  def srvreq(path: String): Future[HttpResponse] = {
    http.singleRequest(HttpRequest(uri = ClientConfig.hostHttpPath+"/api/"+path))
  }
}

object NetworkActor {

}

case class CommandHttpResponse(initial: CmdMessage, hr: HttpResponse)
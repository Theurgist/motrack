package cc.theurgist.motrack.ui.network

import akka.actor.{ActorContext, ActorRef}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import cc.theurgist.motrack.ui.actors.Command
import cc.theurgist.motrack.ui.config.ClientConfig

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Allows actor to send http requests and route an answer to itself or other actor
  *
  * @param actorContext ActorContext of owning actor for initializations
  */
class Requester(actorContext: ActorContext) {
  private val http                                                = Http(actorContext.system)
  private implicit val executionContext: ExecutionContextExecutor = actorContext.dispatcher
  import akka.pattern.pipe

  /**
    * Prepares and executes request
    *
    * @param path server relative API path
    * @return
    */
  private def srvreq(path: String): Future[HttpResponse] = {
    http.singleRequest(HttpRequest(uri = ClientConfig.hostHttpPath + "/api/" + path))
  }

  /**
    * Request and push answer to sending actor
    *
    * @param path server relative API path
    * @param cmdOnCallback returning command
    * @return
    */
  def reqForSelf(path: String, cmdOnCallback: Command)(implicit ac: ActorContext): Future[CommandHttpResponse] = {
    reqFor(path, cmdOnCallback, ac.self)
  }

  /**
    * Request and reroute answer to another actor
    *
    * @param path server relative API path
    * @param cmdOnCallback returning command
    * @param recipient receiver actor
    * @return
    */
  def reqFor(path: String, cmdOnCallback: Command, recipient: ActorRef)(implicit ac: ActorContext): Future[CommandHttpResponse] = {
    val sender = ac.sender()
    srvreq(path).map(r => CommandHttpResponse(cmdOnCallback, r, sender)).pipeTo(recipient)(ac.self)
  }

}

case class CommandHttpResponse(initial: Command, hr: HttpResponse, initiator: ActorRef)

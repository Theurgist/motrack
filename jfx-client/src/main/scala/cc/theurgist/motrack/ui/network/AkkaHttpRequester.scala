package cc.theurgist.motrack.ui.network

import akka.actor.{Actor, ActorContext, ActorRef}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._

import scala.util.{Failure, Success}
import cc.theurgist.motrack.ui.actors.Cmd
import cc.theurgist.motrack.ui.config.ClientConfig
import cc.theurgist.motrack.ui.network.AkkaHttpRequester.{Req, ReqResponse}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.language.{implicitConversions, postfixOps}

object AkkaHttpRequester {

  /**
    * Response wrapper
    *
    * @param initial   command on which request has been initialized
    * @param initiator actor which initiated this request
    * @param r result - either failure or succeeded http response
    */
  case class ReqResponse(initial: Cmd, initiator: ActorRef, r: Either[Throwable, HttpResponse])

  case class Req(
      method: HttpMethod,
      apiPath: String,
      cmdOnCallback: Cmd,
      entity: RequestEntity = HttpEntity.Empty,
      headers: Seq[HttpHeader] = Seq[HttpHeader]()
  )

  implicit def FutureToFutureOps[T](f: Future[T])(implicit executionContext: ExecutionContext): FutureOps[T] =
    new FutureOps[T](f)

  /**
    * Support passthrough of failed command on error
    */
  private class FutureOps[T](val future: Future[T])(implicit executionContext: ExecutionContext) {
    def pipeTo(recipient: ActorRef, initiator: ActorRef, cmdOnFailure: Cmd)(
        implicit sender: ActorRef = Actor.noSender): Future[T] = {
      future.andThen {
        case Success(r) => recipient ! r
        case Failure(f) => recipient ! ReqResponse(cmdOnFailure, initiator, Left(f)) //Status.Failure(f)
      }
    }
  }
}

/**
  * Allows actor to send http requests and route an answer to itself or other actor
  *
  * @param actorContext ActorContext of owning actor for initializations
  */
class AkkaHttpRequester(actorContext: ActorContext) {
  private val http                                  = Http(actorContext.system)
  private implicit val ec: ExecutionContextExecutor = actorContext.dispatcher

  /**
    * Request and push answer to sending actor
    *
    * @param path server relative API path
    * @param cmdOnCallback returning command
    * @return
    */
  def reqForSelf(path: String, cmdOnCallback: Cmd)(implicit ac: ActorContext): Future[ReqResponse] = {
    reqFor(path, cmdOnCallback, ac.self)
  }
  def reqForSelf(method: HttpMethod,
                 apiPath: String,
                 cmdOnCallback: Cmd,
                 entity: RequestEntity = HttpEntity.Empty,
                 headers: Seq[HttpHeader])(implicit ac: ActorContext): Future[ReqResponse] = {
    val initiator = ac.sender()
    http
      .singleRequest(
        HttpRequest(
          method,
          ClientConfig.hostHttpPath + "/api/" + apiPath,
          scala.collection.immutable.Seq(headers: _*),
          entity
        ))
      .map(r => ReqResponse(cmdOnCallback, initiator, Right(r)))
      .pipeTo(ac.self, initiator, cmdOnCallback)(ac.self)
    reqFor(apiPath, cmdOnCallback, ac.self)
  }

  def !(r: Req)(implicit ac: ActorContext): Future[ReqResponse] = {
    import AkkaHttpRequester._
    val initiator = ac.sender()
    http
      .singleRequest(
        HttpRequest(
          r.method,
          ClientConfig.hostHttpPath + "/api/" + r.apiPath,
          scala.collection.immutable.Seq(r.headers: _*),
          r.entity
        ))
      .map(response => ReqResponse(r.cmdOnCallback, initiator, Right(response)))
      .pipeTo(ac.self, initiator, r.cmdOnCallback)(ac.self)
  }

  /**
    * Request and reroute answer to another actor
    *
    * @param path server relative API path
    * @param cmdOnCallback returning command
    * @param recipient receiver actor
    * @return
    */
  def reqFor(path: String, cmdOnCallback: Cmd, recipient: ActorRef)(implicit ac: ActorContext): Future[ReqResponse] = {
    val initiator = ac.sender()
    serveRequest(path)
      .map(r => ReqResponse(cmdOnCallback, initiator, Right(r)))
      .pipeTo(recipient, initiator, cmdOnCallback)(ac.self)
  }

  /**
    * Prepares and executes request
    *
    * @param path server relative API path
    * @return
    */
  private def serveRequest(path: String): Future[HttpResponse] = {
    http.singleRequest(HttpRequest(uri = ClientConfig.hostHttpPath + "/api/" + path))
  }

}

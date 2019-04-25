package cc.theurgist.motrack.ui.network

import akka.actor.{Actor, ActorContext, ActorRef}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import cc.theurgist.motrack.lib.messages.Cmd
import cc.theurgist.motrack.ui.config.ClientConfig
import cc.theurgist.motrack.ui.network.AkkaHttpRequester._
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.{ExecutionContext, Future}
import scala.language.{implicitConversions, postfixOps}
import scala.util.{Failure, Success}

object AkkaHttpRequester {

  sealed trait ReqOutcome

  /**
    * Successful response wrapper
    *
    * @param initial   command on which request has been initialized
    * @param initiator actor which initiated this request
    * @param r result of succeeded http response
    */
  case class ReqResponse(initial: Cmd, initiator: ActorRef, r: HttpResponse) extends ReqOutcome

  /**
    * Failed response wrapper
    *
    * @param initial   command on which request has been initialized
    * @param initiator actor which initiated this request
    * @param error failure description
    */
  case class ReqFailure(initial: Cmd, initiator: ActorRef, error: Throwable) extends ReqOutcome

  case class Req(
      method: HttpMethod,
      apiPath: String,
      cmdOnCallback: Cmd,
      entity: RequestEntity = HttpEntity.Empty,
      headers: Seq[HttpHeader] = Seq[HttpHeader]()
  )

  private implicit def FutureToFutureOps[T](f: Future[T])(implicit executionContext: ExecutionContext): FutureOps[T] =
    new FutureOps[T](f)

  /**
    * Support passthrough of failed command on error (pipeTo pattern analogue)
    */
  private class FutureOps[T](val future: Future[T])(implicit executionContext: ExecutionContext) {
    def pipeWithErr(recipient: ActorRef, initiator: ActorRef, cmd: Cmd)(implicit sender: ActorRef = Actor.noSender): Future[T] =
      future.andThen {
        case Success(r) =>
          recipient ! r
        case Failure(f) =>
          recipient ! ReqFailure(cmd, initiator, f) //Status.Failure(f)
      }
  }
}

/**
  * Allows actor to send http requests and route an answer to itself or other actor
  *
  * @param actorContext ActorContext of owning actor for initializations
  */
class AkkaHttpRequester(actorContext: ActorContext)(implicit ec: ExecutionContext = actorContext.dispatcher)
    extends StrictLogging {
  private val http = Http(actorContext.system)

  /**
    * Request and push answer to sending actor
    */
  def !(request: Req)(implicit ac: ActorContext): Future[ReqResponse] = {
    val initiator = ac.sender()
    val hr = HttpRequest(
      request.method,
      ClientConfig.hostApiPath(request.apiPath),
      scala.collection.immutable.Seq(request.headers: _*),
      request.entity
    )
    logger.info(s"REQ: $hr")

    http
      .singleRequest(hr)
      .map(response => ReqResponse(request.cmdOnCallback, initiator, response))
      .pipeWithErr(ac.self, initiator, request.cmdOnCallback)(ac.self)
  }

}

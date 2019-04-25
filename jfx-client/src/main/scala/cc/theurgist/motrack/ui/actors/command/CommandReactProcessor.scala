package cc.theurgist.motrack.ui.actors.command

import akka.actor.Status.Failure
import akka.actor.{Actor, PoisonPill}
import akka.event.LoggingAdapter
import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, RequestEntity}
import akka.stream.Materializer
import cc.theurgist.motrack.lib.messages.{Exit, LoginAttempt, Logoff, Terminate, UpdateServerStatus}
import cc.theurgist.motrack.ui.network.AkkaHttpRequester
import cc.theurgist.motrack.ui.network.AkkaHttpRequester.Req
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.duration._
import scala.language.{implicitConversions, postfixOps}

trait CommandReactProcessor extends Actor {
  protected def log: LoggingAdapter
  protected implicit def m: Materializer
  protected def http: AkkaHttpRequester

  implicit def entity(json: Json): RequestEntity = HttpEntity(ContentTypes.`application/json`, json.toString())

  def react: Receive = {
    case cmd: UpdateServerStatus =>
      http ! Req(GET, "info/status", cmd)

    case cmd: LoginAttempt =>
      http ! Req(POST, "security/do-login", cmd, cmd.data.asJson)

    case Logoff =>
      http ! Req(POST, "security/logoff", Logoff)

    case Exit =>
      log.info("Exiting application")
      if (sender() != self) sender() ! PoisonPill
      context.system.scheduler.scheduleOnce(1 seconds, self, Terminate)(context.dispatcher)
    case Terminate => context.system.terminate()

    case Failure(error) =>
      log.error(s"CMD got failure:: $error")

    case unrecognized =>
      log.error(s"CMD got unrecognized message: $unrecognized; ${unrecognized.getClass}")
  }
}

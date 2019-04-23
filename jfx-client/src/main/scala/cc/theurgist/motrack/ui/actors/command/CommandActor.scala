package cc.theurgist.motrack.ui.actors.command

import akka.actor.{Actor, Props}
import akka.event.{Logging, LoggingReceive}
import akka.stream.Materializer
import cc.theurgist.motrack.lib.model.security.session.SessionId
import cc.theurgist.motrack.lib.model.security.user.SafeUser
import cc.theurgist.motrack.ui.actors._
import cc.theurgist.motrack.ui.network.AkkaHttpRequester

import scala.language.{implicitConversions, postfixOps}

case class SessionedMessage[T](
    user: SafeUser,
    sessionId: SessionId,
    data: T
)

/**
  * Contains root stateless application behaviour
  */
class CommandActor(implicit val m: Materializer)
    extends Actor with CommandReactProcessor with CommandResponseProcessor with ActorIdent {
  protected val log  = Logging(context.system, this)
  protected val http = new AkkaHttpRequester(context)

  /**
    * Chained message processing pipeline
    */
  override def receive: Receive = LoggingReceive { ident.orElse(response.orElse(react)) }

}

object CommandActor {
  def props(implicit m: Materializer): Props = Props(new CommandActor())
}

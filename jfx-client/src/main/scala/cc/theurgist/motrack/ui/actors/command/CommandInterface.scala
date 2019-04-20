package cc.theurgist.motrack.ui.actors.command

import akka.actor.{ActorRef, ActorSystem}
import cats.effect.IO
import cc.theurgist.motrack.ui.actors.UpdateServerStatus
import akka.actor._
import akka.stream.Materializer
import com.typesafe.scalalogging.StrictLogging

/**
  * Interaction interface
  *
  * @param actor command actor
  * @param owner actor which will receive command execution results
  */
class CommandInterface private (actor: ActorRef)(implicit owner: ActorRef) extends StrictLogging {
  logger.trace(s"CommandInterface's actor: $actor")

  /**
    * Let another actor have it's own copy of this interface
    */
  def lend(forOwner: ActorRef): CommandInterface = new CommandInterface(actor)(forOwner)

  def updateServerStatus(): Unit = {
    actor ! UpdateServerStatus
  }
}

object CommandInterface {
  def init(name: String, owner: ActorRef = ActorRef.noSender)(implicit system: ActorSystem,
                                                              m: Materializer): IO[CommandInterface] =
    for {
      ref <- IO(system.actorOf(CommandActor.props, name))
      res <- IO(new CommandInterface(ref)(owner))
      //_   <- IO(ref ! 'listActors)
    } yield res
}

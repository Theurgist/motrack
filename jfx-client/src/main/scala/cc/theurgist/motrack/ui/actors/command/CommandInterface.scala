package cc.theurgist.motrack.ui.actors.command

import akka.actor.{ActorRef, ActorSystem}
import cats.effect.IO
import cc.theurgist.motrack.ui.actors.UpdateServerStatus
import akka.actor._
import akka.stream.Materializer
import com.typesafe.scalalogging.StrictLogging

class CommandInterface private (actor: ActorRef) extends StrictLogging {
  logger.trace(s"CommandInterface's actor: $actor")

  def updateServerStatus(implicit sender: ActorRef): Unit = {
    actor ! UpdateServerStatus(sender)
  }
}

object CommandInterface {
  def init(name: String)(implicit system: ActorSystem, m: Materializer): IO[CommandInterface] =
    for {
      ref <- IO(system.actorOf(CommandActor.props, name))
      res <- IO(new CommandInterface(ref))
      //_   <- IO(ref ! 'listActors)
    } yield res
}

package cc.theurgist.motrack.ui.actors.command

import akka.actor.{ActorRef, ActorSystem}
import cats.effect.IO
import cc.theurgist.motrack.ui.actors.UpdateServerStatus


class CommandInterface private(actor: ActorRef) {
  def updateServerStatus(implicit sender: ActorRef): Unit = actor ! UpdateServerStatus(sender)
}

object CommandInterface {
  def init(name: String)(implicit system: ActorSystem): IO[CommandInterface] =
    for {
      ref <- IO(system.actorOf(CommandActor.props, name))
      res <- IO(new CommandInterface(ref))
    } yield res
}
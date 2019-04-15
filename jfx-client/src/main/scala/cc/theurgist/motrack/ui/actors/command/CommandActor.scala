package cc.theurgist.motrack.ui.actors.command

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import akka.event.Logging
import cc.theurgist.motrack.ui.actors.network.NetworkActor
import cc.theurgist.motrack.ui.actors.ui.UiActor

class CommandActor extends Actor {
  private val log = Logging(context.system, this)

  val uiActor: ActorRef = context.system.actorOf(Props(classOf[UiActor]), "UI")
  val networkActor: ActorRef = context.system.actorOf(Props[NetworkActor], "NW")




  override def receive: Receive = {
    case UpdateServerStatus =>
      log.info("Pinging..")
      networkActor ! _

    case Exit =>
      log.info("Exiting application")
      self ! PoisonPill

    case wrongMsg => log.error(s"got unrecognized message: $wrongMsg")
  }
}

object CommandActor {
  def props(): Props = Props(new CommandActor)

}
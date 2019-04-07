package cc.theurgist.motrack.ui

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import cc.theurgist.motrack.ui.CommandActor.{Exit, UpdateStatus}

class CommandActor extends Actor {
  private val log = Logging(context.system, this)

  val uiActor: ActorRef = context.system.actorOf(Props(classOf[UiActor]), "UI")
  val networkActor: ActorRef = context.system.actorOf(Props[NetworkActor], "NW")




  override def receive: Receive = {
    case UpdateStatus =>

    case Exit => log.info("Exiting application")
    case wrongMsg => log.error(s"got unrecognized message: $wrongMsg")
  }
}

object CommandActor {
  def props(): Props = Props(new CommandActor)

  case object Exit
  case class UpdateStatus()
}
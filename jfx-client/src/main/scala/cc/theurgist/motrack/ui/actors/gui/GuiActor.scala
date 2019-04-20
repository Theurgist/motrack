package cc.theurgist.motrack.ui.actors.gui

import akka.actor.{Actor, ActorIdentity, ActorPath, Identify, Props}
import akka.event.{Logging, LoggingReceive}
import cats.effect.IO
import cc.theurgist.motrack.lib.dto.ServerStatus
import cc.theurgist.motrack.ui.actors.UpdateServerStatus
import cc.theurgist.motrack.ui.actors.command.CommandActor
import cc.theurgist.motrack.ui.gui.controllers.MainWindowActions

class GuiActor(controller: => Option[MainWindowActions]) extends Actor {
  private val log = Logging(context.system, this)

  override def receive: Receive = LoggingReceive {

//    case 'listActors =>
//      context.actorSelection("/user/*") ! Identify()
//
//    case path: ActorPath =>
//      context.actorSelection(path / "*") ! Identify()
//
//    case ActorIdentity(_, Some(ref)) =>
//      log.info("Got actor " + ref.path.toString)
//      self ! ref.path

    case m: UpdateServerStatus =>
      log.info(s"Server status: $m")

    case ss: ServerStatus =>
      log.info(s"Server status: $ss")
      controller.foreach(_.updateServerStatus())

    case wrongMsg =>
      log.error(s"GUI got unrecognized message: $wrongMsg")
  }
}

object GuiActor {
  //def props: Props = Props[GuiActor]
  def props(controller: => Option[MainWindowActions]): Props = Props(new GuiActor(controller))
}


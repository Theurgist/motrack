package cc.theurgist.motrack.ui.actors.gui

import akka.actor.{Actor, Props}
import akka.event.{Logging, LoggingReceive}
import cc.theurgist.motrack.lib.dto.ServerStatus
import cc.theurgist.motrack.ui.actors.UpdateServerStatus
import cc.theurgist.motrack.ui.gui.controllers.MainWindowActions

class GuiActor(controller: => Option[MainWindowActions]) extends Actor {
  private val log = Logging(context.system, this)

  override def receive: Receive = LoggingReceive {
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
  def props(controller: => Option[MainWindowActions]): Props = Props(new GuiActor(controller))
}

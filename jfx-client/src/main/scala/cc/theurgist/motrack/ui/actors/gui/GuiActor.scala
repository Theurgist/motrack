package cc.theurgist.motrack.ui.actors.gui

import akka.actor.{Actor, Props}
import akka.event.Logging
import cc.theurgist.motrack.ui.actors.UpdateServerStatus

class GuiActor extends Actor {
  private val log = Logging(context.system, this)

  override def receive: Receive = {
    case m: UpdateServerStatus =>
      log.info(s"Server status: $m")
    case wrongMsg =>
      log.error(s"GUI got unrecognized message: $wrongMsg")
  }
}

object GuiActor {
  def props: Props = Props[GuiActor]
}


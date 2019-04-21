package cc.theurgist.motrack.ui.actors.gui

import akka.actor.{Actor, Props}
import akka.event.{Logging, LoggingReceive}
import cc.theurgist.motrack.lib.dto.ServerStatus
import cc.theurgist.motrack.ui.gui.controllers.MainWindowController
import scalafx.application.Platform

class GuiActor(controller: => Option[MainWindowController]) extends Actor {
  private val log = Logging(context.system, this)

  override def receive: Receive = LoggingReceive {
    case ss: ServerStatus =>
      log.debug(s"Server status: $ss")
      guiExec(_.updateServerStatus(ss))

    case wrongMsg =>
      log.error(s"GUI got unrecognized message: $wrongMsg")
  }

  /**
    * Ensures running GUI-related actions on javafx thread via controller
    */
  def guiExec(action: MainWindowController => Unit): Unit = {
    controller.foreach(c => Platform.runLater(action(c)))
  }
}

object GuiActor {
  def props(controller: => Option[MainWindowController]): Props = Props(new GuiActor(controller))
}

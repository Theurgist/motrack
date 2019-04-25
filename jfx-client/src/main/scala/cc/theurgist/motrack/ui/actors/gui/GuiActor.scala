package cc.theurgist.motrack.ui.actors.gui

import akka.actor.{FSM, Props}
import cc.theurgist.motrack.lib.dto.ServerStatus
import cc.theurgist.motrack.lib.model.security.session.SessionId
import cc.theurgist.motrack.lib.model.security.user.SafeUser
import cc.theurgist.motrack.ui.actors.{LoginResult, Logoff, ServerError}
import cc.theurgist.motrack.ui.gui.controllers.MainWindowController
import scalafx.application.Platform

trait GuiState
case object LoggedOff                 extends GuiState
case class LogoffError(error: String) extends GuiState
case object LoggedIn                  extends GuiState

trait GuiData
case class Uninitialized(lastLogin: String) extends GuiData
case class UserData(sid: SessionId, user: SafeUser)         extends GuiData

/**
  * Abstracted GUI interaction and FSM
  *
  * @param controller main window controller
  */
class GuiActor(controller: => Option[MainWindowController]) extends FSM[GuiState, GuiData] {
  startWith(LoggedOff, Uninitialized(""))

  when(LoggedOff) {
    case Event(LoginResult(r), _) => r match {
      case Right((sid, user)) =>
        goto(LoggedIn).using(UserData(sid, user))
      case Left(error) =>
        guiExec(_.loginPageController.updateError(s"Login error: $error"))
        stay()
    }
  }

  when(LoggedIn) {
    case Event(Logoff, d: UserData) =>
      goto(LoggedOff).using(Uninitialized(d.user.login))
  }

  whenUnhandled {
    case Event(ss: ServerStatus, _) =>
      guiExec(_.updateServerStatus(ss))
      stay

    case Event(ServerError(msg), _) =>
      guiExec(_.updateErrorLabel(msg))
      stay

    case wrongMsg =>
      log.error(s"GUI got unrecognized message: $wrongMsg")
      stay
  }

  /**
    * Run GUI-related action on javafx thread via controller
    */
  private def guiExec(action: MainWindowController => Unit): Unit = controller.foreach(c => Platform.runLater(action(c)))
}

object GuiActor {
  def props(controller: => Option[MainWindowController]): Props = Props(new GuiActor(controller))
}

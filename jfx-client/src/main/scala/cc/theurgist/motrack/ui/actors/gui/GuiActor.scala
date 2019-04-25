package cc.theurgist.motrack.ui.actors.gui

import akka.actor.{FSM, Props}
import cc.theurgist.motrack.lib.dto.ServerStatus
import cc.theurgist.motrack.lib.messages.{LoginResult, Logoff, ServersideError}
import cc.theurgist.motrack.lib.model.security.session.SessionId
import cc.theurgist.motrack.lib.model.security.user.SafeUser
import cc.theurgist.motrack.lib.security.SecBundle
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
    case Event((osb: Option[SecBundle], LoginResult(r)), _) => r match {
      case Right((sid, user)) =>
        guiExec(mw => {
          mw.changeSec(osb)
          mw.gotoLoggedInEnv(user)
        })
        goto(LoggedIn).using(UserData(sid, user))
      case Left(error) =>
        guiExec(mw => {
          mw.loginPageController.updateError(error)
          mw.gotoLoggedOffEnv()
        })
        stay()
    }
  }

  when(LoggedIn) {
    case Event(Logoff, d: UserData) =>
      guiExec(mw => {
        mw.changeSec(None)
        mw.gotoLoggedOffEnv()
      })
      goto(LoggedOff).using(Uninitialized(d.user.login))
  }

  whenUnhandled {
    case Event(e: ServersideError, _) =>
      guiExec(_.ssLabelController.updateErrorLabel(e.msg))
      stay

    case Event(ss: ServerStatus, _) =>
      guiExec(_.updateServerStatus(ss))
      stay

    case Event(ServersideError(msg), _) =>
      guiExec(_.updateErrorLabel(msg))
      stay

    case wrongMsg =>
      val msg = s"GUI got unrecognized message: $wrongMsg"
      log.error(msg)
      guiExec(_.updateErrorLabel(msg))
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

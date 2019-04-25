package cc.theurgist.motrack.ui.actors

import cc.theurgist.motrack.lib.model.security.session.SessionId
import cc.theurgist.motrack.lib.model.security.user.SafeUser
import cc.theurgist.motrack.lib.security.LoginData

sealed trait Cmd
sealed trait CmdOutcome


case class LoginAttempt(data: LoginData) extends Cmd
case class LoginResult(r: Either[String, (SessionId, SafeUser)]) extends CmdOutcome
case object Logoff extends Cmd

case class UpdateServerStatus() extends Cmd
case class ServerError(message: String) extends Cmd
case object Exit extends Cmd
case object Terminate extends Cmd


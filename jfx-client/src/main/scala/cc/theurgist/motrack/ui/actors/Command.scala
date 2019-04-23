package cc.theurgist.motrack.ui.actors

import cc.theurgist.motrack.lib.model.security.user.SafeUser
import cc.theurgist.motrack.lib.security.LoginData

sealed trait Command

case class LoginAttempt(data: LoginData) extends Command
case class LoginSuccess(user: SafeUser) extends Command
case class LoginFailure(error: String) extends Command
case object Logoff extends Command

case class UpdateServerStatus() extends Command
case class ServerError(message: String) extends Command
case object Exit extends Command
case object Terminate extends Command

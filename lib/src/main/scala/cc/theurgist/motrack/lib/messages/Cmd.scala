package cc.theurgist.motrack.lib.messages

import cc.theurgist.motrack.lib.security.LoginData

sealed trait Cmd

case class LoginAttempt(data: LoginData) extends Cmd
case object Logoff extends Cmd

case class UpdateServerStatus() extends Cmd
case class ServerError(message: String) extends Cmd

case object GetAccounts extends Cmd

case object Exit extends Cmd
case object Terminate extends Cmd

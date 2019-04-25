package cc.theurgist.motrack.lib.messages

import cc.theurgist.motrack.lib.model.account.Account
import cc.theurgist.motrack.lib.model.security.session.SessionId
import cc.theurgist.motrack.lib.model.security.user.SafeUser

sealed trait CmdOutcome

case class LoginResult(r: Either[String, (SessionId, SafeUser)]) extends CmdOutcome
case class ServersideError(msg: String) extends CmdOutcome

case class AccountsList(l: List[Account]) extends CmdOutcome
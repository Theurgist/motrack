package cc.theurgist.motrack.ui.actors.command

sealed trait CmdMessage

case class UpdateServerStatus() extends CmdMessage
case class Exit() extends CmdMessage

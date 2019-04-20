package cc.theurgist.motrack.ui.actors

sealed trait Command

case class UpdateServerStatus() extends Command
case class Exit() extends Command

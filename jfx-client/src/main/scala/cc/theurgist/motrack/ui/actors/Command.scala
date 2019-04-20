package cc.theurgist.motrack.ui.actors

import akka.actor.ActorRef

sealed trait Command

case class UpdateServerStatus() extends Command
case class Exit() extends Command

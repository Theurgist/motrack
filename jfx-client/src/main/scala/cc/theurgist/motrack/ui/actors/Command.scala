package cc.theurgist.motrack.ui.actors

import akka.actor.ActorRef

sealed trait Command

case class UpdateServerStatus(sender: ActorRef) extends Command
case class Exit() extends Command

package cc.theurgist.motrack.ui.actors

import akka.actor.Actor.Receive
import akka.actor.{ActorContext, ActorIdentity, ActorPath, Identify}
import akka.event.LoggingAdapter

/**
  * Implements actors identification logic via partial function
  */
trait ActorIdent {

  /**
    * Message for actors identification start
    */
  case object ListActorsMessage
  protected def log: LoggingAdapter

  def ident(implicit ac: ActorContext): Receive = {
    case ListActorsMessage      => ac.actorSelection("/user/*") ! Identify(())
    case path: ActorPath        => ac.actorSelection(path / "*") ! Identify(())
    case ActorIdentity(_, None) => // No children
    case ActorIdentity(_, Some(ref)) =>
      log.info(s"Identified actor: ${ref.path}")
      ac.self ! ref.path // Query children
  }

}

package cc.theurgist.motrack.ui

import akka.actor.Actor
import akka.event.Logging

class NetworkActor extends Actor {
  private val log = Logging(context.system, this)

  override def receive: Receive = {
    case wrongMsg => log.error(s"got unrecognized message: $wrongMsg")
  }
}

object NetworkActor {

}
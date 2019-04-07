package cc.theurgist.motrack.ui

import akka.actor.{Actor, ActorRef}
import akka.event.Logging

class UiActor extends Actor {
  private val log = Logging(context.system, this)
  log.info(s"Summoning UiApp: ${UiApp.toString}")
  UiApp.main(Array())

  override def receive: Receive = {
    case wrongMsg => log.error(s"got unrecognized message: $wrongMsg")
  }
}

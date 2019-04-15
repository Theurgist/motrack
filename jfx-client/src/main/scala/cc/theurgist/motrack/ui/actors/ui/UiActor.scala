package cc.theurgist.motrack.ui.actors.ui

import akka.actor.Actor
import akka.event.Logging
import akka.http.scaladsl.Http
import cc.theurgist.motrack.ui.ui.UiApp

class UiActor extends Actor {
  private val log = Logging(context.system, this)
  log.info(s"Summoning UiApp: ${UiApp.toString}")
  UiApp.main(Array())

  override def receive: Receive = {
    case wrongMsg => log.error(s"got unrecognized message: $wrongMsg")
  }
}

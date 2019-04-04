package cc.theurgist.motrack.ui.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class UiRootRoute {
  def apply(): Route =
    get {
      path("") {
        complete("Requested to Hello")
      }
    }
}

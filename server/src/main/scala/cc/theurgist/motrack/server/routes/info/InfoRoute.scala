package cc.theurgist.motrack.server.routes.info

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

class InfoRoute {
  def apply(): Route =
    get {
      path("status") {
        complete(ServerStatus())
      }
    }
}

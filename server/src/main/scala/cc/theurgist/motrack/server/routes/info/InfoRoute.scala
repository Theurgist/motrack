package cc.theurgist.motrack.server.routes.info

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cc.theurgist.motrack.lib.dto.{Green, ServerStatus}
import cc.theurgist.motrack.server.routes.RouteBranch
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

class InfoRoute extends RouteBranch{
  def route: Route =
    get {
      path("status") {
        complete(ServerStatus(Green, "Online"))
      }
    }
}

package cc.theurgist.motrack.server.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cc.theurgist.motrack.server.routes.info.InfoRoute

class ServerRootRoute {
  def apply(): Route =
    path("") {
      complete("Requested for Hello")
    } ~ pathPrefix("user") {
      new UsersRoute()()
    } ~ pathPrefix("info") {
      new InfoRoute()()
    } ~ pathPrefix("account") {
      new AccountsRoot()()
    } ~ pathPrefix("zha") {
      path(IntNumber) { id =>
        complete(s"ZHHHA №$id")
      }
    }

}

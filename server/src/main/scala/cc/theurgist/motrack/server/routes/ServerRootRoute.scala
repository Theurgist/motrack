package cc.theurgist.motrack.server.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cc.theurgist.motrack.server.routes.info.InfoRoute
import cc.theurgist.motrack.server.routes.security.{SecurityRoute, UsersRoute}

import scala.concurrent.ExecutionContextExecutor

class ServerRootRoute(implicit ec: ExecutionContextExecutor) {

  def r: Route =
    path("") {
      complete("Requested for Hello")
    } ~ pathPrefix("api") {
      pathPrefix("security") {
        new SecurityRoute
      } ~ pathPrefix("user") {
        new UsersRoute
      } ~ pathPrefix("info") {
        new InfoRoute
      } ~ pathPrefix("account") {
        new AccountsRoot
      } ~ pathPrefix("zha") {
        path(IntNumber) { id =>
          complete(s"ZHHHA №$id")
        }
      }
    }

}

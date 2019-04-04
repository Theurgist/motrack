package cc.theurgist.motrack.server.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cc.theurgist.motrack.server.routes.info.Info

class ServerRootRoute {
  def apply(): Route =
    get {
      path("") {
        complete("Requested to Hello")
      } ~ pathPrefix("user") {
        new Users()()
      } ~ pathPrefix("info") {
        new Info()()
      } ~ pathPrefix("zha") {
        path(IntNumber) {id =>
          complete(s"ZHHHA №$id")
        }
      }
    } ~ post {
      path("") {
        complete("Posted for Hello")
      }
    }
}

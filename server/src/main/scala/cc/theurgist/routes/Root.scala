package cc.theurgist.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class Root {
  def apply(): Route =
    get {
      path("") {
        complete("Requested to Hello")
      } ~ pathPrefix("user") {
        new Users()()
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

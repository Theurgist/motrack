package cc.theurgist.motrack.server.routes

import akka.event.Logging
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.{DebuggingDirectives, LogEntry}
import cc.theurgist.motrack.server.routes.info.InfoRoute
import cc.theurgist.motrack.server.routes.security.{SecurityRoute, UsersRoute}

import scala.concurrent.ExecutionContextExecutor

class ServerRootRoute(implicit ec: ExecutionContextExecutor) {
  def requestMethodAsInfo(req: HttpRequest): LogEntry = LogEntry(s"${req.method.name}: ${req.uri} {${req.entity}} [${req.headers}]", Logging.InfoLevel)

  def r: Route = DebuggingDirectives.logRequest(requestMethodAsInfo _) {
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

}

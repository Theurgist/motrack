package cc.theurgist.motrack.server.routes.security

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cc.theurgist.motrack.lib.model.security.user.UserId
import cc.theurgist.motrack.server.database.Db
import cc.theurgist.motrack.server.database.Db.InmemContext
import cc.theurgist.motrack.server.database.dal.UserDAO
import cc.theurgist.motrack.server.routes.RouteBranch
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

import scala.concurrent.ExecutionContextExecutor

class UsersRoute(implicit ec: ExecutionContextExecutor) extends RouteBranch {
  implicit val dbCtx: InmemContext = Db.getInmemCtx.get
  private val users = new UserDAO

  def route: Route =
    get {
      parameter("id".as[Int]) { id =>
        complete(users.find(new UserId(id)).get)
      } ~ parameter("name".as[String]) { name =>
        complete(users.find(name).get)
      }
    }
}

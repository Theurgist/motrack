package cc.theurgist.motrack.server.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cc.theurgist.motrack.lib.model.security.user.UserId
import cc.theurgist.motrack.server.database.Db
import cc.theurgist.motrack.server.database.dal.UserDAO
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

class UsersRoute {
  private val users = new UserDAO(Db.getInmemCtx.get)

  def apply(): Route =
    get {
      parameter("id".as[Int]) { id =>
        complete(users.find(new UserId(id)).get)
      } ~ parameter("name".as[String]) { name =>
        complete(users.find(name).get)
      }
    }
}

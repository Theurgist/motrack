package cc.theurgist.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cc.theurgist.database.Db
import cc.theurgist.database.dal.UserDAO
import cc.theurgist.model.security.UserId
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

class Users {
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

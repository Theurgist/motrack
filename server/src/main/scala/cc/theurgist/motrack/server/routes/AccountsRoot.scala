package cc.theurgist.motrack.server.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cc.theurgist.motrack.lib.Timing
import cc.theurgist.motrack.lib.model.account.{Account, AccountId}
import cc.theurgist.motrack.server.database.Db
import cc.theurgist.motrack.server.database.dal.AccountDAO
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

class AccountsRoot extends RouteBranch {
  private val accounts = new AccountDAO(Db.getInmemCtx.get)

  def route: Route =
    get {
      path(IntNumber) { id =>
        accounts.find(new AccountId(id)) match {
          case Some(acc) => complete(acc)
          case None      => complete(StatusCodes.NotFound)
        }
      }
    } ~ post {
      path("") {
        entity(as[Account]) { acc =>
          val cleanedId = acc.copy(id = AccountId.none, createdAt = Timing.now)
          accounts.insert(cleanedId) match {
            case id if id != AccountId.none => complete(StatusCodes.Created, s"${id()}")
            case _                          => complete(StatusCodes.InternalServerError)
          }
        }
      } ~ path("new") {
        entity(as[Account]) { acc =>
          val cleanedId = acc.copy(id = AccountId.none, createdAt = Timing.now)
          accounts.insert(cleanedId) match {
            case id if id != AccountId.none => complete(StatusCodes.Created, s"${id()}")
            case _ => complete(StatusCodes.InternalServerError)
          }
        }
      }
    }
//      post {
//        path("new") {
//          entity(as[Account]) { acc =>
//            val cleanedId = acc.copy(id = AccountId.none, createdAt = Timing.now)
//            accounts.insert(cleanedId) match {
//              case id if id != AccountId.none => complete(StatusCodes.Created, s"${id()}")
//              case _ => complete(StatusCodes.InternalServerError)
//            }
//          }
//        } ~
//        parameter("owner".as[Int], "currency".as[Int], "name".as[String], "type".as[String]) { (o, c, n, t) =>
//          val a = Account(
//            AccountId.none,
//            new UserId(o),
//            new CurrencyId(c),
//            n,
//            AccountType(t),
//            Timing.now
//          )
//          accounts.insert(a)
//          complete("ok")
//        }
//      }
}

package cc.theurgist.motrack.server.routes.security

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cc.theurgist.motrack.lib.Timing
import cc.theurgist.motrack.lib.model.security.session.{Session, SessionId}
import cc.theurgist.motrack.lib.security.LoginData
import cc.theurgist.motrack.server.database.dal.{SessionDAO, UserDAO}
import cc.theurgist.motrack.server.routes.RouteBranch
import com.softwaremill.session.CsrfDirectives._
import com.softwaremill.session.CsrfOptions._
import com.softwaremill.session.SessionDirectives._
import com.softwaremill.session.SessionOptions._
import com.typesafe.scalalogging.StrictLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

import scala.concurrent.ExecutionContextExecutor

class SecurityRoute(implicit val ec: ExecutionContextExecutor) extends RouteBranch with SessionProvider with StrictLogging {
  private val (ux, sx) = (new UserDAO, new SessionDAO)

  def route: Route =
    path("do-login") {
      post {
        entity(as[LoginData]) { ld =>
          ux.find(ld.username) match {
            case Some(u) =>
              val s   = Session(SessionId.none, u.id, Timing.now, Timing.now)
              val sid = sx.create(s)
              mySetSession(sid.id) {
                setNewCsrfToken(checkHeader) { ctx =>
                  ctx.complete((sid.id, u.safePart))
                }
              }
            case None =>
              complete(StatusCodes.Forbidden)
          }
        }
      }
    } ~ path("logoff") {
      post {
        myRequiredSession { s =>
          myInvalidateSession { ctx =>
            sx.delete(new SessionId(s))
            logger.info(s"Logging out $s")
            ctx.complete("ok")
          }
        }
      }
    } ~ path("whoami") {
      get {
        requiredSession(refreshable, usingHeaders) { s => ctx =>
          (for {
            session <- sx.find(new SessionId(s))
            u       <- ux.find(session.userId)
          } yield u.safePart) match {
            case Some(u) =>
              ctx.complete(u)
            case None =>
              ctx.complete(StatusCodes.Unauthorized)
          }
        }
      }
    }
}

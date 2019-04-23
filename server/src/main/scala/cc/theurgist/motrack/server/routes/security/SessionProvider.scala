package cc.theurgist.motrack.server.routes.security

import akka.http.scaladsl.server.{Directive0, Directive1}
import cc.theurgist.motrack.server.config.SrvConfig
import cc.theurgist.motrack.server.database.Db
import cc.theurgist.motrack.server.database.Db.InmemContext
import com.softwaremill.session.SessionDirectives.{invalidateSession, requiredSession, setSession}
import com.softwaremill.session.{InMemoryRefreshTokenStorage, SessionConfig, SessionManager}
import com.softwaremill.session.SessionOptions.{refreshable, usingCookies, usingHeaders}
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.ExecutionContextExecutor

/**
  * Mixin for RouteBranch with session check capabilities
  */
trait SessionProvider extends StrictLogging {
  implicit def ec: ExecutionContextExecutor
  private val scfg = SessionConfig.default(SrvConfig.secret)

  implicit val dbCtx: InmemContext                                    = Db.getInmemCtx.get
  implicit val sessionManager: SessionManager[Long]                   = new SessionManager[Long](scfg)
  implicit val refreshTokenStorage: InMemoryRefreshTokenStorage[Long] = (msg: String) => logger.info(s"SEC: $msg")

  def mySetSession(v: Long): Directive0   = setSession(refreshable, usingHeaders, v)
  val myRequiredSession: Directive1[Long] = requiredSession(refreshable, usingHeaders)
  val myInvalidateSession: Directive0     = invalidateSession(refreshable, usingHeaders)

}
